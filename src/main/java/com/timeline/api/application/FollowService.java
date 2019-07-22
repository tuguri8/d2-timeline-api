package com.timeline.api.application;

import com.timeline.api.interfaces.dto.response.FollowListResponse;
import com.timeline.api.interfaces.dto.response.FollowUserResponse;
import com.timeline.api.interfaces.dto.response.FollowerListResponse;

import java.util.List;

public interface FollowService {
    FollowUserResponse followUser(String userId, String followId);

    FollowUserResponse unFollowUser(String userId, String unFollowId);

    List<FollowListResponse> getFollowList(String userId);

    List<FollowerListResponse> getFollowerList(String userId);
}
