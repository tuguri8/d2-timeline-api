package com.timeline.api.Post.domain;

import com.timeline.api.Post.interfaces.dto.request.PostRequest;
import com.timeline.api.Post.service.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<PostModel,Long> {
    Optional<PostModel> findByPostId(Long PostId);
}
