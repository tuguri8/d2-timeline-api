package com.timeline.api.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class PostSendingToKafka {

    private Integer follwers;
    private PostingMessage postingMessage;

    public PostSendingToKafka(Integer follwers) {
        this.follwers = follwers;
    }

    private KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig(follwers);

    private KafkaTemplate<String, PostingMessage> postingMessageKafkaTemplate = kafkaProducerConfig.postingKafkaTemplate();

    @Value(value = "${message.topic.name}")
    private String postingTopicName;

    public void sendPostingMessageToKafka(PostingMessage postingMessage) {
        postingMessageKafkaTemplate.send(postingTopicName, postingMessage);
    }

    public Integer getFollwers() {
        return follwers;
    }

    public void setFollwers(Integer follwers) {
        this.follwers = follwers;
    }
    public PostingMessage getPostingMessage() {
        return postingMessage;
    }

    public void setPostingMessage(PostingMessage postingMessage) {
        this.postingMessage = postingMessage;
    }
}
