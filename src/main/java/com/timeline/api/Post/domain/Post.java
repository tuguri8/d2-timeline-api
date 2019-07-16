package com.timeline.api.Post.domain;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;
    private String userId; // Account 테이블 참조
    private String userName; //Account 테이블 참조
    @Lob
    private String content;
    private LocalDateTime postDatetime;
    private String imagePath;

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName;}

    public String getContent() { return content; }
    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPostDatetime() { return postDatetime; }
    public void setPostDatetime (LocalDateTime postDatetime) {
        this.postDatetime = postDatetime;
    }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
