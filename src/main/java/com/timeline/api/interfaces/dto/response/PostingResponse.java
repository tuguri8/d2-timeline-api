package com.timeline.api.interfaces.dto.response;

import java.util.UUID;

public class PostingResponse {

    public UUID postId;
    public String content;

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
