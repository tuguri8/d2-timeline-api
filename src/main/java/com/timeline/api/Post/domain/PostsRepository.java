package com.timeline.api.Post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<Post,Long> {
    Optional<Post> findByUserId(String UserId);
}
