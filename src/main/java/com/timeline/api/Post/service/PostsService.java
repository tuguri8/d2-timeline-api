package com.timeline.api.Post.service;

import com.timeline.api.Post.interfaces.dto.request.PostRequest;
import com.timeline.api.Post.interfaces.dto.response.PostResponse;

public interface PostsService {
    PostResponse savePost (PostRequest postRequest);
}

