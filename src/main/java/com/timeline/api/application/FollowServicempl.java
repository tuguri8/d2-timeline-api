//package com.timeline.api.application;
//
//import com.timeline.api.domain.entity.Account;
//import com.timeline.api.domain.entity.Follow;
//import com.timeline.api.infrastructure.repository.AccountRepository;
//import com.timeline.api.infrastructure.repository.FollowRepository;
//import com.timeline.api.interfaces.dto.response.FollowListResponse;
//import com.timeline.api.interfaces.dto.response.FollowUserResponse;
//import com.timeline.api.interfaces.dto.response.FollowerListResponse;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.stream.Collectors;
//
//@Service
//public class FollowServicempl implements FollowService {
//
//    private final AccountRepository accountRepository;
//    private final FollowRepository followRepository;
//    private final ModelMapper modelMapper;
//
//    public FollowServicempl(AccountRepository accountRepository,
//                            FollowRepository followRepository,
//                            ModelMapper modelMapper) {
//        this.accountRepository = accountRepository;
//        this.followRepository = followRepository;
//        this.modelMapper = modelMapper;
//    }
//
//    @Override
//    public FollowUserResponse followUser(String userId, String followId) {
//        Follow follow = new Follow();
//        follow.setUserId(userId);
//        follow.setFollowId(followId);
//        followRepository.save(follow);
//        return transform(follow);
//    }
//
//    @Override
//    @Transactional
//    public FollowUserResponse unFollowUser(String userId, String unFollowId) {
//        Follow follow = followRepository.findByUserIdAndFollowId(userId, unFollowId)
//                                        .orElseThrow(() -> new NoSuchElementException("팔로우 하지 않은 회원입니다."));
//        followRepository.delete(follow);
//        return transform(follow);
//    }
//
//    @Override
//    public List<FollowListResponse> getFollowList(String userId) {
//        List<Follow> followList = followRepository.findByUserId(userId).orElse(Collections.emptyList());
//        return followList.stream()
//                         .map(this::followListTransform)
//                         .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<FollowerListResponse> getFollowerList(String userId) {
//        List<Follow> followerList = followRepository.findByFollowId(userId).orElse(Collections.emptyList());
//        return followerList.stream()
//                           .map(this::followerListTransform)
//                           .collect(Collectors.toList());
//    }
//
//    private FollowUserResponse transform(Follow follow) {
//        Account followAccount = accountRepository.findByUserId(follow.getFollowId())
//                                                 .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원 입니다."));
//        FollowUserResponse followUserResponse = new FollowUserResponse();
//        followUserResponse.setFollowName(followAccount.getUserName());
//        return followUserResponse;
//    }
//
//    private FollowListResponse followListTransform(Follow follow) {
//        Account followAccount = accountRepository.findByUserId(follow.getFollowId())
//                                                 .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원 입니다."));
//        return modelMapper.map(followAccount, FollowListResponse.class);
//    }
//
//    private FollowerListResponse followerListTransform(Follow follow) {
//        Account followAccount = accountRepository.findByUserId(follow.getFollowId())
//                                                 .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원 입니다."));
//        return modelMapper.map(followAccount, FollowerListResponse.class);
//    }
//}
