package com.timeline.api.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    private PostingMessage postingMessage;

    @Value(value = "${message.topic.name}")
    private String postingTopicName;

    private final KafkaTemplate<String, PostingMessage> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, PostingMessage> kafkaTemplate) {this.kafkaTemplate = kafkaTemplate;}

    public void sendMessage(PostingMessage postingMessage) {
        ListenableFuture<SendResult<String, PostingMessage>> future = kafkaTemplate.send(postingTopicName, postingMessage);

        future.addCallback(new ListenableFutureCallback<SendResult<String, PostingMessage>>() {

            @Override
            public void onSuccess(SendResult<String, PostingMessage> result) {
                log.info("Sent message= [" + postingMessage.toString() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("Message 전달 오류 " + postingMessage.toString() + "] due to : " + ex.getMessage());
            }
        });

    }

    public PostingMessage getPostingMessage() {
        return postingMessage;
    }

    public void setPostingMessage(PostingMessage postingMessage) {
        this.postingMessage = postingMessage;
    }
}
