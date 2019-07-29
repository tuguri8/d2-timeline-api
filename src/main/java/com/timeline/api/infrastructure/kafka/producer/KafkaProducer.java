package com.timeline.api.infrastructure.kafka.producer;

import com.timeline.api.application.model.PostingMessageModel;
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

    @Value(value = "${message.topic.name}")
    private String postingTopicName;

    private final KafkaTemplate<String, PostingMessageModel> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, PostingMessageModel> kafkaTemplate) {this.kafkaTemplate = kafkaTemplate;}

    public void sendMessage(PostingMessageModel postingMessageModel) {
        ListenableFuture<SendResult<String, PostingMessageModel>> future = kafkaTemplate.send(postingTopicName, postingMessageModel);

        future.addCallback(new ListenableFutureCallback<SendResult<String, PostingMessageModel>>() {

            @Override
            public void onSuccess(SendResult<String, PostingMessageModel> result) {
                log.info("Sent message= [" + postingMessageModel.toString() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("Message 전달 오류 " + postingMessageModel.toString() + "] due to : " + ex.getMessage());
            }
        });

    }
}
