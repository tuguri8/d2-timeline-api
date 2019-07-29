package com.timeline.api.kafka;

import java.util.List;
import java.util.UUID;

public class PostingMessage {
    private List<String> followerId;
    private UUID postId;

    public PostingMessage() {
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
        return "PostingMessage{" +
            "followerId=" + followerId +
            ", postId=" + postId +
            '}';
    }
}
