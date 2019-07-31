package com.timeline.api.application.service;

import com.timeline.api.application.exception.ServiceException;
import com.timeline.api.domain.entity.Account;
import com.timeline.api.domain.entity.Follow;
import com.timeline.api.infrastructure.repository.AccountRepository;
import com.timeline.api.infrastructure.repository.FollowRepository;
import com.timeline.api.interfaces.dto.response.FollowListResponse;
import com.timeline.api.interfaces.dto.response.FollowUserResponse;
import com.timeline.api.interfaces.dto.response.FollowerListResponse;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowServicempl implements FollowService {

    private final AccountRepository accountRepository;
    private final FollowRepository followRepository;
    private final ModelMapper modelMapper;

    public FollowServicempl(AccountRepository accountRepository,
                            FollowRepository followRepository,
                            ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.followRepository = followRepository;
        this.modelMapper = modelMapper;
    }

    @Caching(evict = {@CacheEvict(value = "follow", key = "#userId"), @CacheEvict(value = "follower", key = "#followId")})
    @Override
    @Transactional
    public FollowUserResponse followUser(String userId, String followId) {
        Account user = accountRepository.findByUserId(userId).orElseThrow(ServiceException.UserIsNotExistException::new);
        Account followUser = accountRepository.findByUserId(followId).orElseThrow(ServiceException.UserIsNotExistException::new);
        if (followRepository.findByUserAndFollow(user, followUser).isPresent()) {
            throw new ServiceException.UserIsNotExistException();
        }
        Follow follow = new Follow();
        follow.setUser(user);
        follow.setFollow(followUser);
        followRepository.save(follow);
        return modelMapper.map(followUser, FollowUserResponse.class);
    }

    @Caching(evict = {@CacheEvict(value = "follow", key = "#userId"), @CacheEvict(value = "follower", key = "#unFollowId")})
    @Override
    @Transactional
    public FollowUserResponse unFollowUser(String userId, String unFollowId) {
        Account user = accountRepository.findByUserId(userId).orElseThrow(ServiceException.UserIsNotExistException::new);
        Account unFollowUser = accountRepository.findByUserId(unFollowId).orElseThrow(ServiceException.UserIsNotExistException::new);
        Follow follow = followRepository.findByUserAndFollow(user, unFollowUser)
                                        .orElseThrow(ServiceException.NotFollowException::new);
        followRepository.delete(follow);
        return modelMapper.map(unFollowUser, FollowUserResponse.class);
    }

    @Cacheable(value = "follow", key = "#userId")
    @Override
    public List<FollowListResponse> getFollowList(String userId) {
        Account user = accountRepository.findByUserId(userId).orElseThrow(ServiceException.UserIsNotExistException::new);
        List<Follow> followList = followRepository.findByUser(user).orElse(Collections.emptyList());
        return followList.stream()
                         .map(follow -> modelMapper.map(follow.getFollow(), FollowListResponse.class))
                         .collect(Collectors.toList());
    }

    @Cacheable(value = "follower", key = "#userId")
    @Override
    public List<FollowerListResponse> getFollowerList(String userId) {
        Account user = accountRepository.findByUserId(userId).orElseThrow(ServiceException.UserIsNotExistException::new);
        List<Follow> followerList = followRepository.findByFollow(user).orElse(Collections.emptyList());

        return followerList.stream()
                           .map(follower -> modelMapper.map(follower.getUser(), FollowerListResponse.class))
                           .collect(Collectors.toList());
    }
}
