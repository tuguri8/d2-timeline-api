package com.timeline.api.interfaces;

import com.timeline.api.application.service.PostService;
import com.timeline.api.interfaces.dto.request.PostRequest;
import com.timeline.api.interfaces.dto.response.HomePostResponse;
import com.timeline.api.interfaces.dto.response.PostingResponse;
import com.timeline.api.interfaces.support.ExtractUserFromAuth;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public PostingResponse posting(@RequestBody PostRequest postRequest, Authentication authentication) {
        return postService.savePost(ExtractUserFromAuth.getUserNameFromAuthentication(authentication), postRequest.getContent());
    }

    // 홈 게시글 조회
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<HomePostResponse> getHomePostList(Authentication authentication) {
        return postService.getHomePostList(ExtractUserFromAuth.getUserNameFromAuthentication(authentication));
    }
}
