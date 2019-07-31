package com.timeline.api.domain.entity;

import com.datastax.driver.core.utils.UUIDs;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tomcat.jni.Local;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Table
public class Post implements Serializable {

    private static final long serialVersionUID = -14238791237843278L;

    @PrimaryKeyColumn(name = "YEAR_MONTH", type = PrimaryKeyType.PARTITIONED)
    private String yearMonth;

    @Column("CONTENT")
    private String content;

    @PrimaryKeyColumn(name = "POST_ID", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private UUID postId;

    @PrimaryKeyColumn(name = "USER_ID", type = PrimaryKeyType.CLUSTERED)
    private String userId;

    @Column("USERNAME")
    private String userName;

    @Column("CREATED_AT")
    private LocalDateTime createdDate;

    public Post() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYYMM");
        this.createdDate = LocalDateTime.now();
        this.postId = UUIDs.timeBased();
        this.yearMonth = LocalDate.now().format(formatter);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
