package com.timeline.api.infrastructure.repository;

import com.timeline.api.domain.entity.Home;
import org.springframework.data.repository.CrudRepository;

public interface HomeRepository extends CrudRepository<Home, String> {
}
