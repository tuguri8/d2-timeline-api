package com.timeline.api.domain.entity;

import com.timeline.api.domain.entity.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table
public class Follow extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -14238791237843171L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 기준 유저 계정
    @JoinColumn(name = "USER", nullable = false)
    @ManyToOne
    private Account user;

    // 친구 유저 계정
    @JoinColumn(name = "FOLLOW", nullable = false)
    @ManyToOne
    private Account follow;

    public Follow() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public Account getFollow() {
        return follow;
    }

    public void setFollow(Account follow) {
        this.follow = follow;
    }
}
