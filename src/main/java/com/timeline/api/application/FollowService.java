package com.timeline.api.application;

import com.timeline.api.interfaces.dto.response.FollowUserResponse;

public interface FollowService {
    FollowUserResponse followUser(String userId, String followId);
}
