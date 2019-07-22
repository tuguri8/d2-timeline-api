package com.timeline.api.application.service;

import com.timeline.api.domain.entity.Post;
import com.timeline.api.infrastructure.repository.PostRepository;
import com.timeline.api.interfaces.dto.response.PostingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostingResponse savePost(String userId, String content) {
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(content);
        postRepository.save(post);
        return modelMapper.map(post, PostingResponse.class);
    }
}
