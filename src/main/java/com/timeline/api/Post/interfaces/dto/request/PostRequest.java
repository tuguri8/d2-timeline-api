package com.timeline.api.Post.interfaces.dto.request;

public class PostRequest {
    //내 소식 (내용만 쓰는 경우)
    private String content;

    public String getContent() { return content; }
    public void setContent(String content) {
        this.content = content;
    }
}
