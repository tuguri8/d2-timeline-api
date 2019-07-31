package com.timeline.api.application.service;

import com.timeline.api.application.model.UserModel;
import com.timeline.api.interfaces.dto.response.UserListResponse;

import java.util.List;

public interface UserService {
    UserModel signUpUser(String userId, String userName, String password);

    List<UserListResponse> getUserList(String name);
}
