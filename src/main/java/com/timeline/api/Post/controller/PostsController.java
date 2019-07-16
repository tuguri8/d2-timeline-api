package com.timeline.api.Post.controller;

import com.timeline.api.Post.domain.Post;
import com.timeline.api.Post.domain.PostsRepository;
import com.timeline.api.Post.service.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostsController {

    PostsService postsService;
    PostsRepository postsRepository;

    public PostsController(PostsService postsService,PostsRepository postsRepository) {
        this.postsService = postsService;
        this.postsRepository=postsRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(){
        //임의로 모든 포스트 리스트 출력
        List<Post> posts =postsRepository.findAll();
        //postList.jsp 필요
    return new ModelAndView("postList","posts",posts);
    }

    //작성 페이지 (GET)
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView writeForm() {

        //writeform.jsp 리턴
        return new ModelAndView("writeform");
    }

    //게시글 작성 (to Database)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String write(@ModelAttribute("Post") Post post) {
        postsService.savePost(post);

        return "redirct://localhost:8080/post";
    }
}
