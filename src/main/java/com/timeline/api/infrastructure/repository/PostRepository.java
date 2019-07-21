package com.timeline.api.infrastructure.repository;

import com.timeline.api.domain.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, String> {
}
