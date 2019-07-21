package com.timeline.api.application.model;

import com.timeline.api.domain.entity.Account;

public class AcceptFriendsModel {
    private Account user;
    private Account friend;
    private Boolean isActive;

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
