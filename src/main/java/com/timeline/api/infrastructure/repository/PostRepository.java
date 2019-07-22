package com.timeline.api.infrastructure.repository;

import com.timeline.api.domain.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, String> {
    Optional<List<Post>> findByUserIdAndTimestampDay(String userId, Long timestampDay);
}
