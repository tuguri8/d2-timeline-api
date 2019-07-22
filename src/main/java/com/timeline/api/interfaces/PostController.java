package com.timeline.api.interfaces;

import com.timeline.api.application.service.PostService;
import com.timeline.api.interfaces.dto.request.PostRequest;
import com.timeline.api.interfaces.dto.response.PostingResponse;
import com.timeline.api.interfaces.support.ExtractUserFromAuth;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //게시글 작성
    @PostMapping
    public PostingResponse posting(@RequestBody PostRequest postRequest, Authentication authentication) {
        return postService.savePost(ExtractUserFromAuth.getUserNameFromAuthentication(authentication), postRequest.getContent());
    }
}
