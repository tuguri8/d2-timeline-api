package com.timeline.api.domain.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Table
public class Timeline implements Serializable {

    private static final long serialVersionUID = -14238791237812299L;

    @PrimaryKeyColumn(name = "USER_ID", type = PrimaryKeyType.PARTITIONED)
    private String userId;

    @PrimaryKeyColumn(name = "TIMESTAMP_HOUR", type = PrimaryKeyType.CLUSTERED)
    private Long timestamp_hour;

    @Column("POST_ID")
    private UUID postId;

    public Timeline() {
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

    public Long getTimestamp_hour() {
        return timestamp_hour;
    }

    public void setTimestamp_hour(Long timestamp_hour) {
        this.timestamp_hour = timestamp_hour;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }
}
