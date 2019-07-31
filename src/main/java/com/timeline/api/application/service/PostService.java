package com.timeline.api.application.service;

import com.timeline.api.interfaces.dto.response.HomePostResponse;
import com.timeline.api.interfaces.dto.response.PostingResponse;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostingResponse savePost(String userId, String content);
    List<HomePostResponse> getHomePostList(String userId);
    void insertToTimelineTable(UUID postId, String follower);
}

