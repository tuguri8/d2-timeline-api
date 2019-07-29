package com.timeline.api.application.service;

import com.timeline.api.domain.entity.Home;
import com.timeline.api.domain.entity.Post;
import com.timeline.api.domain.entity.Timeline;
import com.timeline.api.infrastructure.repository.HomeRepository;
import com.timeline.api.infrastructure.repository.PostRepository;
import com.timeline.api.infrastructure.repository.TimelineRepository;
import com.timeline.api.interfaces.dto.response.FollowerListResponse;
import com.timeline.api.interfaces.dto.response.PostingResponse;
import com.timeline.api.kafka.KafkaProducer;
import com.timeline.api.kafka.PostingMessage;
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
    private final TimelineRepository timelineRepository;
    private final KafkaProducer kafkaProducer;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper,
                           HomeRepository homeRepository,
                           FollowService followService,
                           TimelineRepository timelineRepository,
                           KafkaProducer kafkaProducer
    ) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.homeRepository = homeRepository;
        this.followService = followService;
        this.timelineRepository = timelineRepository;
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
        PostingMessage postingMessage = new PostingMessage();
        postingMessage.setPostId(post.getPostId());
        postingMessage.setFollowerId(followerList.stream()
                                                 .map(FollowerListResponse::getUserId)
                                                 .collect(Collectors.toList()));
        kafkaProducer.sendMessage(postingMessage);
//        log.info("타임라인에 저장된 개수 : " + savePostToFollowerTimeline(userId, post.getPostId()).size());
        return modelMapper.map(post, PostingResponse.class);
    }

    // 홈 저장소에 게시물 번호 저장
    private void savePostToUserHome(String userId, UUID postId) {
        Home home = new Home();
        home.setPostId(postId);
        home.setUserId(userId);
        homeRepository.save(home);
    }

    // 팔로워 들의 타임라인에 게시물 번호 추가(TTL)
    private List<Timeline> savePostToFollowerTimeline(String userId, UUID postId) {
        List<FollowerListResponse> followerList = followService.getFollowerList(userId);
        return followerList.stream()
                           .map(follower -> timelineRepository.saveWithTTL(follower.getUserId(), postId))
                           .collect(Collectors.toList());
    }

    //-----------------------------kafka 연동---------------------------------//

    //post 테이블에 저장후 kafka서버로 (작성자 id, 팔로워 id , 포스트 id ) 메시지 한꺼번에 전송
//    @Override
//    @Transactional
//    public PostingResponse savePostAndSendToKafka(String userId, String content) {
//
//        Post post = new Post();
//        post.setUserId(userId);
//        post.setContent(content);
//        postRepository.save(post);
//        savePostToUserHome(userId, post.getPostId());
//
//        List<FollowerListResponse> followerList = followService.getFollowerList(userId);
//
//        //한꺼번에 보내기 위함(배치 사이즈 : 팔로워수)
//        for (FollowerListResponse follower : followerList) {
//            PostingMessage postingMessage = new PostingMessage(userId, follower.getUserId(), post.getPostId());
//            KafkaProducer postSendingToKafka = new KafkaProducer(followerList.size());
//            postSendingToKafka.sendMessage(postingMessage);
//        }
//
//        return modelMapper.map(post, PostingResponse.class);
//    }

    // 팔로워 1명의 타임라인에 게시물 번호 추가(TTL)
    @KafkaListener(topics = "${message.topic.name}", groupId = "posting")//containerFactory = "postingKafkaListenerContainerFactory"
    public void postingListener(PostingMessage postingMessage) {
        UUID stringID = postingMessage.getPostId();
        List<String> followers = postingMessage.getFollowerId();
        for (int i = 0; i < followers.size(); i++) {
            timelineRepository.saveWithTTL(followers.get(i), stringID);
        }
//        CountDownLatch postingLatch = new CountDownLatch(1);
//        timelineRepository.saveWithTTL(postingMessage.getReceiverId(), postingMessage.getPostId());
//        postingLatch.countDown();
        log.info("타임라인에 저장 완료, 저장 개수 : " + followers.size());
//        return timelineRepository.findByUserId(postingMessage.getReceiverId()).orElse(Collections.emptyList());
    }
}
