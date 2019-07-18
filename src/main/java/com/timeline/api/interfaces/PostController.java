package com.timeline.api.interfaces;

import com.timeline.api.domain.PostRepository;
import com.timeline.api.interfaces.dto.request.PostRequest;
import com.timeline.api.interfaces.dto.response.PostResponse;
import com.timeline.api.application.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //게시글 작성
    @PostMapping
    public PostResponse posting(@RequestBody PostRequest postRequest) {
        return postService.savePost(postRequest);
    }
}
