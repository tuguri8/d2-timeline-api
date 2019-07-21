package com.timeline.api.application;

import com.timeline.api.domain.entity.Follow;
import com.timeline.api.infrastructure.repository.AccountRepository;
import com.timeline.api.infrastructure.repository.FollowRepository;
import com.timeline.api.interfaces.dto.response.FollowUserResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FollowServicemplTest {

    @Autowired
    private FollowService followService;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void setUp() {
        userService.signUpUser("tuguri5", "김길동", "asdf12");
        userService.signUpUser("tuguri6", "홍길동", "asdf12");
    }

    @Test
    public void 팔로우_테스트() {
        followService.followUser("tuguri5", "tuguri6");
        assertThat(followRepository.findByUserId("tuguri5").isPresent()).isTrue();
    }
}