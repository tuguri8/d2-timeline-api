package com.timeline.api.infrastructure.repository;

import com.timeline.api.domain.entity.Timeline;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimelineRepository extends CrudRepository<Timeline, String> {
    @Query("INSERT INTO timeline (user_id, post_id) VALUES (?0, ?1) USING TTL 43200")
    Timeline saveWithTTL(String userId, UUID postId);

    Optional<List<Timeline>> findByUserId(String userId);

}
