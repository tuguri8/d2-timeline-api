package com.timeline.api.application.service;

import com.timeline.api.interfaces.dto.response.PostingResponse;

public interface PostService {
    PostingResponse savePost(String userId, String content);
//    PostingResponse savePostAndSendToKafka(String userId, String content);
}

