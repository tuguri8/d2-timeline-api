package com.timeline.api.interfaces.dto.response;

import com.timeline.api.application.model.AddFriendsModel;

public class AddFriendsResponse {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public AddFriendsResponse(AddFriendsModel addFriendsModel) {
        this.userName = addFriendsModel.getFriend().getUserName();
    }
}
