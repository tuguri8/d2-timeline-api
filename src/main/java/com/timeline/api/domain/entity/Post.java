package com.timeline.api.domain.entity;

import org.apache.tomcat.jni.Local;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Table
public class Post implements Serializable {

    private static final long serialVersionUID = -14238791237843278L;

    @PrimaryKeyColumn(name = "USER_ID", type = PrimaryKeyType.PARTITIONED)
    private String userId;

    @PrimaryKeyColumn(name = "TIMESTAMP_DAY", type = PrimaryKeyType.CLUSTERED)
    private Long timestamp_day;

    @Column("CONTENT")
    private String content;

    @Column("POST_ID")
    private UUID postId;

    @Column("CREATED_DATE")
    private LocalDateTime createdDate;

    public Post() {
        this.createdDate = LocalDateTime.now();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTimestamp_day() {
        return timestamp_day;
    }

    public void setTimestamp_day(Long timestamp_day) {
        this.timestamp_day = timestamp_day;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
