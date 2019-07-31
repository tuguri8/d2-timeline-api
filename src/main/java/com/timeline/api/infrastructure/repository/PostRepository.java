package com.timeline.api.infrastructure.repository;

import com.timeline.api.domain.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends CrudRepository<Post, String> {
    Optional<List<Post>> findByYearMonth(String yearMonth);
    Optional<List<Post>> findByYearMonthAndPostIdIn(String yearMonth, List<UUID> postIdList);
}
