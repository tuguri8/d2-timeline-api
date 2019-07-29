package com.timeline.api.application.service;

import com.timeline.api.application.model.PostingMessageModel;
import com.timeline.api.domain.entity.Home;
import com.timeline.api.domain.entity.Post;
import com.timeline.api.domain.entity.Timeline;
import com.timeline.api.infrastructure.repository.HomeRepository;
import com.timeline.api.infrastructure.repository.PostRepository;
import com.timeline.api.infrastructure.repository.TimelineRepository;
import com.timeline.api.interfaces.dto.response.FollowerListResponse;
import com.timeline.api.interfaces.dto.response.PostingResponse;
import com.timeline.api.infrastructure.kafka.producer.KafkaProducer;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final HomeRepository homeRepository;
    private final FollowService followService;
    private final KafkaProducer kafkaProducer;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper,
                           HomeRepository homeRepository,
                           FollowService followService,
                           KafkaProducer kafkaProducer
    ) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.homeRepository = homeRepository;
        this.followService = followService;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    @Transactional
    public PostingResponse savePost(String userId, String content) {
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(content);
        postRepository.save(post);
        savePostToUserHome(userId, post.getPostId());

        List<FollowerListResponse> followerList = followService.getFollowerList(userId);
        PostingMessageModel postingMessageModel = new PostingMessageModel();
        postingMessageModel.setPostId(post.getPostId());
        postingMessageModel.setFollowerId(followerList.stream()
                                                      .map(FollowerListResponse::getUserId)
                                                      .collect(Collectors.toList()));
        kafkaProducer.sendMessage(postingMessageModel);
        return modelMapper.map(post, PostingResponse.class);
    }

    // 홈 저장소에 게시물 번호 저장
    private void savePostToUserHome(String userId, UUID postId) {
        Home home = new Home();
        home.setPostId(postId);
        home.setUserId(userId);
        homeRepository.save(home);
    }
}
