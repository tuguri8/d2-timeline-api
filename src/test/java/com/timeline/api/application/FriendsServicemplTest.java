package com.timeline.api.application;

import com.timeline.api.domain.entity.Account;
import com.timeline.api.infrastructure.repository.AccountRepository;
import com.timeline.api.domain.entity.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FriendsServicemplTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    FriendsRepository friendsRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Before
    public void setUp() {
        Account account1 = new Account("tuguri1", "홍길동", passwordEncoder.encode("abcd12"), UserRole.USER);
        Account account2 = new Account("tuguri2", "김길동", passwordEncoder.encode("abcd12"), UserRole.USER);
        accountRepository.saveAll(Arrays.asList(account1, account2));
    }

    @Test
    public void 친구_추가_테스트() {
        Account user = accountRepository.findByUserId("tuguri1").orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        Account friend = accountRepository.findByUserId("tuguri2").orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        Friends friends = new Friends();
        friends.setUser(user);
        friends.setFriend(friend);
        friends.setActive(false);
        friendsRepository.save(friends);
        assertTrue(friendsRepository.findAll().size() > 0);
    }


}