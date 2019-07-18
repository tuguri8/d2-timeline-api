package com.timeline.api.interfaces.dto.response;

public class PostResponse {

    public Long postId;
    public String content;

    public Long getPostId() {return postId;}

    public void setPostId(Long postId) {this.postId = postId;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

}
