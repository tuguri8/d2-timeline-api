package com.timeline.api.infrastructure.repository;

import com.timeline.api.domain.entity.Follow;
import org.springframework.data.repository.CrudRepository;

public interface FollowRepository extends CrudRepository<Follow, String> {
}
