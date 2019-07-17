package com.timeline.api.Post.controller;

import com.timeline.api.Post.domain.PostsRepository;
import com.timeline.api.Post.interfaces.dto.request.PostRequest;
import com.timeline.api.Post.interfaces.dto.response.PostResponse;
import com.timeline.api.Post.service.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/api")
public class PostsController {

    PostsService postsService;
    PostsRepository postsRepository;

    public PostsController(PostsService postsService,PostsRepository postsRepository) {
        this.postsService = postsService;
        this.postsRepository=postsRepository;
    }

    //게시글 작성
    @PostMapping("/post")
    public PostResponse posting(@RequestBody  PostRequest postRequest) {
        return postsService.savePost(postRequest);
    }
}
