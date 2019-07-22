package com.timeline.api.infrastructure.repository;

import com.timeline.api.domain.entity.Account;
import com.timeline.api.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<List<Follow>> findByUser(Account user);
    Optional<Follow> findByUserAndFollow(Account user, Account follow);
    Optional<List<Follow>> findByFollow(Account follow);
}
