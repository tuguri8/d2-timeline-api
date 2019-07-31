package com.timeline.api.interfaces.dto.response;

import com.timeline.api.application.model.UserModel;

public class SignUpResponse {
    private String userName;

    public SignUpResponse(UserModel userModel) {
        this.userName = userModel.getUserName();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
