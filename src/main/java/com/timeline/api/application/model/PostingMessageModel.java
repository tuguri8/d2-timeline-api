package com.timeline.api.application.model;

import java.util.List;
import java.util.UUID;

public class PostingMessageModel {
    private List<String> followerId;
    private UUID postId;

    public PostingMessageModel() {
    }

    public List<String> getFollowerId() {
        return followerId;
    }

    public void setFollowerId(List<String> followerId) {
        this.followerId = followerId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "PostingMessageModel{" +
            "followerId=" + followerId +
            ", postId=" + postId +
            '}';
    }
}
