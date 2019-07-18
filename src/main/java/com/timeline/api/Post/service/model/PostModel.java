package com.timeline.api.Post.service.model;

import com.timeline.api.domain.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="POSTS")
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;
    @JoinColumn(name="UserId")
    private Account user;
    @Lob
    private String content;
    private LocalDateTime postDatetime;

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Account getuser() { return user; }
    public void setuser(Account user) { this.user = user; }

    public String getContent() { return content; }
    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPostDatetime() { return postDatetime; }
    public void setPostDatetime (LocalDateTime postDatetime) {
        this.postDatetime = postDatetime;
    }
}
