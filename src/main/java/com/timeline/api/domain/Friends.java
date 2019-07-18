package com.timeline.api.domain;

import com.timeline.api.domain.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name ="FRIENDS")
public class Friends extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1223489243809L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 기준 유저 계정
    @JoinColumn(name = "USER", nullable = false)
    @ManyToOne
    private Account user;

    // 친구 유저 계정
    @JoinColumn(name = "FRIEND", nullable = false)
    @ManyToOne
    private Account friend;

    // 친구 수락 여부
    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "boolean default false")
    private Boolean isActive;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public Account getFriend() {
        return friend;
    }

    public void setFriend(Account friend) {
        this.friend = friend;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


}
