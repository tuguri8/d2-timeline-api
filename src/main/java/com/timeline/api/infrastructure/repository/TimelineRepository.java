package com.timeline.api.infrastructure.repository;

import com.timeline.api.domain.entity.Timeline;
import org.springframework.data.repository.CrudRepository;

public interface TimelineRepository extends CrudRepository<Timeline, String> {
}
