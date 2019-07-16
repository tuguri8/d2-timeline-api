package com.timeline.api.Post.service;

import com.timeline.api.Post.domain.Post;
import com.timeline.api.Post.domain.PostsRepository;

import java.time.LocalDateTime;

public class postsServiceImpl implements PostsService {

    PostsRepository postsRepository;

    public postsServiceImpl(PostsRepository postsRepository){
        this.postsRepository=postsRepository;
    }

    //게시글 작성 후 DB 저장
    public void savePost (Post post){
    //사용자 인증 거쳤다고 가정 ( userId,userName 받아왔다고 가정)
        // content,imagePath는 @modelAttribute 통해 자동 바인딩
        post.setPostDatetime(LocalDateTime.now());

        postsRepository.save(post);
    }
}
