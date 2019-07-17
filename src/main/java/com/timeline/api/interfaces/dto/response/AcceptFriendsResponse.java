package com.timeline.api.interfaces.dto.response;

import com.timeline.api.application.model.AcceptFriendsModel;

public class AcceptFriendsResponse {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public AcceptFriendsResponse(AcceptFriendsModel acceptFriendsModel) {
        this.userName = acceptFriendsModel.getUser().getUserName();
    }
}
