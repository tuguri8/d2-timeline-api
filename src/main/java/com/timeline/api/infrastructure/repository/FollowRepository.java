package com.timeline.api.infrastructure.repository;

import com.timeline.api.domain.entity.Follow;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FollowRepository extends CrudRepository<Follow, String> {
    Optional<Follow> findByUserId(String userId);
    Optional<Follow> findByUserIdAndFollowId(String userId, String followId);
}
