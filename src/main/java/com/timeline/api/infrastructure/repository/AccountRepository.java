package com.timeline.api.infrastructure.repository;

import com.timeline.api.domain.entity.Account;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByUserId(String userId);
    Optional<List<Account>> findByUserName(String userName);
}
