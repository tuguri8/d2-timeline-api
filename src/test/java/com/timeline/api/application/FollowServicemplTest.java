package com.timeline.api.application;

import com.timeline.api.domain.entity.Account;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
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
    }

    @Test
    public void 팔로우_테스트() {
        followService.followUser("tuguri87", "tuguri5");
        Account testAccount = accountRepository.findByUserId("tuguri87").orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        assertThat(followRepository.findByUser(testAccount).isPresent()).isTrue();
    }

    @Test
    public void 언팔로우_테스트() {
        followService.followUser("tuguri87", "tuguri5");
        followService.unFollowUser("tuguri87", "tuguri5");
        Account testAccount = accountRepository.findByUserId("tuguri87").orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        Account secondTestAccount = accountRepository.findByUserId("tuguri5").orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        assertThat(followRepository.findByUserAndFollow(testAccount, secondTestAccount).isPresent()).isFalse();
    }

    @Test
    public void 팔로우목록_불러오기() {
        followService.followUser("tuguri5", "tuguri87");
        assertThat(followService.getFollowList("tuguri5").size() > 0).isTrue();
    }

    @Test
    public void 팔로워목록_불러오기() {
        followService.followUser("tuguri5", "tuguri87");
        assertThat(followService.getFollowerList("tuguri87").size() > 0).isTrue();
    }
}