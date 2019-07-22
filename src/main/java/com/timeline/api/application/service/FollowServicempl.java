package com.timeline.api.application.service;

import com.timeline.api.domain.entity.Account;
import com.timeline.api.domain.entity.Follow;
import com.timeline.api.infrastructure.repository.AccountRepository;
import com.timeline.api.infrastructure.repository.FollowRepository;
import com.timeline.api.interfaces.dto.response.FollowListResponse;
import com.timeline.api.interfaces.dto.response.FollowUserResponse;
import com.timeline.api.interfaces.dto.response.FollowerListResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
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

    @Override
    @Transactional
    public FollowUserResponse followUser(String userId, String followId) {
        Account user = accountRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        Account followUser = accountRepository.findByUserId(followId).orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        Follow follow = new Follow();
        follow.setUser(user);
        follow.setFollow(followUser);
        followRepository.save(follow);
        return modelMapper.map(followUser, FollowUserResponse.class);
    }

    @Override
    @Transactional
    public FollowUserResponse unFollowUser(String userId, String unFollowId) {
        Account user = accountRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        Account unFollowUser = accountRepository.findByUserId(unFollowId).orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        Follow follow = followRepository.findByUserAndFollow(user, unFollowUser)
                                        .orElseThrow(() -> new NoSuchElementException("팔로우 하지 않은 회원입니다."));
        followRepository.delete(follow);
        return modelMapper.map(unFollowUser, FollowUserResponse.class);
    }

    @Override
    public List<FollowListResponse> getFollowList(String userId) {
        Account user = accountRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        List<Follow> followList = followRepository.findByUser(user).orElse(Collections.emptyList());
        return followList.stream()
                         .map(follow -> modelMapper.map(follow.getFollow(), FollowListResponse.class))
                         .collect(Collectors.toList());
    }

    @Override
    public List<FollowerListResponse> getFollowerList(String userId) {
        Account user = accountRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        List<Follow> followerList = followRepository.findByFollow(user).orElse(Collections.emptyList());
        return followerList.stream()
                           .map(follower -> modelMapper.map(follower.getUser(), FollowerListResponse.class))
                           .collect(Collectors.toList());
    }
}
