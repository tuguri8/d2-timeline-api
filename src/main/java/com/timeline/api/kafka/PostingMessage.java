package com.timeline.api.kafka;

import java.util.UUID;

public class PostingMessage {
    private String senderId;
    private String receiverId;
    private UUID postId;

    public PostingMessage() {
    }

    public PostingMessage(String senderId, String receiverId, UUID postId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.postId = postId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "senderID: " + senderId + ", receiverID: " + receiverId;
    }
}
