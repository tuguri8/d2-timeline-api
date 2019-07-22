package com.timeline.api.domain.entity;

import com.datastax.driver.core.utils.UUIDs;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tomcat.jni.Local;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Table
public class Post implements Serializable {

    private static final long serialVersionUID = -14238791237843278L;

    @PrimaryKeyColumn(name = "TIMESTAMP_DAY", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private Long timestampDay;

    @Column("CONTENT")
    private String content;

    @PrimaryKeyColumn(name = "POST_ID", type = PrimaryKeyType.CLUSTERED)
    private UUID postId;

    @PrimaryKeyColumn(name = "USER_ID", type = PrimaryKeyType.CLUSTERED)
    private String userId;

    @Column("CREATED_AT")
    private LocalDateTime createdDate;

    public Post() {
        this.createdDate = LocalDateTime.now();
        this.postId = UUIDs.timeBased();
        this.timestampDay = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH).getTime() / 1000;;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getTimestampDay() {
        return timestampDay;
    }

    public void setTimestampDay(Long timestampDay) {
        this.timestampDay = timestampDay;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
