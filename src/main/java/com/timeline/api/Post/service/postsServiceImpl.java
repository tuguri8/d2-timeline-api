package com.timeline.api.Post.service;

import com.timeline.api.Post.domain.PostsRepository;
import com.timeline.api.Post.interfaces.dto.request.PostRequest;
import com.timeline.api.Post.interfaces.dto.response.PostResponse;
import com.timeline.api.Post.service.model.PostModel;

import java.time.LocalDateTime;

public class postsServiceImpl implements PostsService {

    PostsRepository postsRepository;

    public postsServiceImpl(PostsRepository postsRepository){
        this.postsRepository=postsRepository;
    }

    //게시글 작성 후 DB 저장
    public PostResponse savePost (PostRequest postRequest){
        PostModel postModel=new PostModel();
        PostResponse postResponse=new PostResponse();

        postModel.setContent(postRequest.getContent());
        //postModel.setuser(); 유저정보 전송
        postModel.setPostDatetime(LocalDateTime.now());
        postsRepository.save(postModel);

        postResponse.setContent(postModel.getContent());
        postResponse.setPostId((postModel.getPostId()));

        return postResponse;
    }
}
