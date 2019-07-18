package com.timeline.api.application;

import com.timeline.api.domain.PostRepository;
import com.timeline.api.interfaces.dto.request.PostRequest;
import com.timeline.api.interfaces.dto.response.PostResponse;
import com.timeline.api.domain.Post;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //게시글 작성 후 DB 저장
    public PostResponse savePost(PostRequest postRequest) {
        Post post = new Post();
        PostResponse postResponse = new PostResponse();

        post.setContent(postRequest.getContent());
        //post.setuser(); 유저정보 전송
        post.setPostDatetime(LocalDateTime.now());
        postRepository.save(post);

        postResponse.setContent(post.getContent());
        postResponse.setPostId((post.getPostId()));

        return postResponse;
    }
}
