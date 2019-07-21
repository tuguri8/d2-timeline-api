package com.timeline.api.domain.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static java.time.ZoneId.systemDefault;

@Table
public class Account implements Serializable {

    private static final long serialVersionUID = -14238791237843141L;

    @PrimaryKeyColumn(name = "USER_ID", type = PrimaryKeyType.PARTITIONED)
    private String userId;

    @Column("USER_NAME")
    private String userName;

    @Column("PASSWORD")
    private String password;

    @Column("USER_ROLE")
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    public Account() {
    }

    public Account(String userId, String userName, String password, UserRole userRole) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
