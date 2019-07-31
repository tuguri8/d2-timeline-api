package com.timeline.api.infrastructure.repository;

import com.timeline.api.domain.entity.Home;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface HomeRepository extends CrudRepository<Home, String> {
    Optional<List<Home>> findByUserId(String userId);
}
