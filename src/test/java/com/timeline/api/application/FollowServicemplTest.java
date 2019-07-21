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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback(value = true)
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
        assertThat(followRepository.findByUserId("tuguri87").isPresent()).isTrue();
        followRepository.delete(followRepository.findByUserIdAndFollowId("tuguri87","tuguri5").get());
    }

    @Test
    public void 언팔로우_테스트() {
        followService.followUser("tuguri87", "tuguri5");
        followService.unFollowUser("tuguri87", "tuguri5");
        assertThat(followRepository.findByUserIdAndFollowId("tuguri87", "tuguri5").isPresent()).isFalse();
    }

    @Test
    public void 팔로우목록_불러오기() {
        followService.followUser("tuguri5", "tuguri87");
        assertThat(followService.getFollowList("tuguri5").size() > 0).isTrue();
        followRepository.delete(followRepository.findByUserIdAndFollowId("tuguri5","tuguri87").get());
    }

    @After
    public void after() {
        accountRepository.delete(accountRepository.findByUserId("tuguri5").get());
    }
}