package com.timeline.api.application;

import com.timeline.api.domain.entity.Account;
import com.timeline.api.domain.entity.Follow;
import com.timeline.api.infrastructure.repository.AccountRepository;
import com.timeline.api.infrastructure.repository.FollowRepository;
import com.timeline.api.interfaces.dto.response.FollowUserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FollowServicempl implements FollowService {

    private final AccountRepository accountRepository;
    private final FollowRepository followRepository;

    public FollowServicempl(AccountRepository accountRepository,
                            FollowRepository followRepository) {
        this.accountRepository = accountRepository;
        this.followRepository = followRepository;
    }

    @Override
    public FollowUserResponse followUser(String userId, String followId) {
        Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setFollowId(followId);
        followRepository.save(follow);
        return transform(follow);
    }

    private FollowUserResponse transform(Follow follow) {
        Account followAccount = accountRepository.findByUserId(follow.getFollowId())
                                                 .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원 입니다."));
        FollowUserResponse followUserResponse = new FollowUserResponse();
        followUserResponse.setFollowName(followAccount.getUserName());
        return followUserResponse;
    }
}
