package com.timeline.api.application.service;

import com.timeline.api.application.exception.ServiceException;
import com.timeline.api.application.model.PostingMessageModel;
import com.timeline.api.domain.entity.Home;
import com.timeline.api.domain.entity.Post;
import com.timeline.api.infrastructure.repository.AccountRepository;
import com.timeline.api.infrastructure.repository.HomeRepository;
import com.timeline.api.infrastructure.repository.PostRepository;
import com.timeline.api.infrastructure.repository.TimelineRepository;
import com.timeline.api.interfaces.dto.response.FollowerListResponse;
import com.timeline.api.interfaces.dto.response.HomePostResponse;
import com.timeline.api.interfaces.dto.response.PostingResponse;
import com.timeline.api.infrastructure.kafka.producer.KafkaProducer;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final HomeRepository homeRepository;
    private final AccountRepository accountRepository;
    private final FollowService followService;
    private final KafkaProducer kafkaProducer;
    private final TimelineRepository timelineRepository;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper,
                           HomeRepository homeRepository,
                           AccountRepository accountRepository, FollowService followService,
                           KafkaProducer kafkaProducer,
                           TimelineRepository timelineRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.homeRepository = homeRepository;
        this.accountRepository = accountRepository;
        this.followService = followService;
        this.kafkaProducer = kafkaProducer;
        this.timelineRepository = timelineRepository;
    }

    // 게시글 입력
    @CacheEvict(value = "home", key = "#userId")
    @Override
    @Transactional
    public PostingResponse savePost(String userId, String content) {
        Post post = new Post();
        post.setUserId(userId);
        post.setUserName(accountRepository.findByUserId(userId).orElseThrow(ServiceException.UserIsNotExistException::new).getUserName());
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

    // 홈 게시글 조회
    @Cacheable(value = "home", key = "#userId")
    @Override
    public List<HomePostResponse> getHomePostList(String userId) {
        List<Home> homeList = homeRepository.findByUserId(userId).orElse(Collections.emptyList());
        // 홈 테이블에서 포스트 ID 조회
        List<UUID> postIdList = homeList.stream()
                                        .map(Home::getPostId)
                                        .collect(Collectors.toList());
        // 포스트 조회
        List<Post> postList = postRepository.findByYearMonthAndPostIdIn(getNowYearMonth(), postIdList)
                                            .orElse(Collections.emptyList());
        return postList.stream()
                       .map(post -> modelMapper.map(post, HomePostResponse.class))
                       .collect(Collectors.toList());
    }

    // 홈 저장소에 게시물 번호 저장
    private void savePostToUserHome(String userId, UUID postId) {
        Home home = new Home();
        home.setPostId(postId);
        home.setUserId(userId);
        homeRepository.save(home);
    }

    // 현재 yearMonth 조회
    private String getNowYearMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYYMM");
        return LocalDate.now().format(formatter);
    }

    // 카프카 리스너로부터 넘어온 팔로워와 postId를 비동기로 테이블에 넣어준다
    @Async
    public void insertToTimelineTable(UUID postId, String follower) {
        timelineRepository.saveWithTTL(follower, postId);
        log.info("타임라인에 저장 완료, 팔로워 : " + follower);
    }
}
