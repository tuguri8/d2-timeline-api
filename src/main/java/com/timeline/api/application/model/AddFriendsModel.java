package com.timeline.api.application.model;

import com.timeline.api.domain.entity.Account;

public class AddFriendsModel {
    private Account user;
    private Account friend;

    public Account getUser() {
        return user;
    }

    public Account getFriend() {
        return friend;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public void setFriend(Account friend) {
        this.friend = friend;
    }
}
