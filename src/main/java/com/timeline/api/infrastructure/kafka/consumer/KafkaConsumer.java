package com.timeline.api.infrastructure.kafka.consumer;

import com.timeline.api.application.model.PostingMessageModel;
import com.timeline.api.infrastructure.repository.TimelineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    private final TimelineRepository timelineRepository;

    public KafkaConsumer(TimelineRepository timelineRepository) {this.timelineRepository = timelineRepository;}

    // 팔로워들의 타임라인에 게시물 번호 추가(TTL)
    @KafkaListener(topics = "${message.topic.name}", groupId = "${kafka.groupId}")
    public void postingListener(PostingMessageModel postingMessageModel) {
        UUID stringID = postingMessageModel.getPostId();
        List<String> followers = postingMessageModel.getFollowerId();
        for (String follower : followers) {
            timelineRepository.saveWithTTL(follower, stringID);
        }
        log.info("타임라인에 저장 완료, 저장 개수 : " + followers.size());
    }
}
