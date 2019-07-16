package com.timeline.api.application;

import com.timeline.api.application.model.UserModel;

public interface UserService {
    UserModel signUpUser(String userId, String userName, String password);
}
