package com.timeline.api.infrastructure.kafka.consumer;

import com.timeline.api.application.model.PostingMessageModel;
import com.timeline.api.application.service.PostService;
import com.timeline.api.infrastructure.repository.TimelineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    private final TimelineRepository timelineRepository;
    private final PostService postService;

    public KafkaConsumer(TimelineRepository timelineRepository, PostService postService) {
        this.timelineRepository = timelineRepository;
        this.postService = postService;
    }

    // 카프카 리스너, PostId와 팔로워 리스트를 타임라인 테이블에 저장하는 async 함수로 넘겨준다
    @KafkaListener(topics = "${message.topic.name}", groupId = "${kafka.groupId}")
    public void postingListener(PostingMessageModel postingMessageModel) {
        log.info("Kafka Consumer Listening");
        UUID stringId = postingMessageModel.getPostId();
        List<String> followers = postingMessageModel.getFollowerId();
        for (String follower : followers) {
            postService.insertToTimelineTable(stringId, follower);
        }
    }
}
