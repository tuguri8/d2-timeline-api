package com.timeline.api.application.service;

import com.datastax.driver.core.utils.UUIDs;
import com.timeline.api.infrastructure.repository.HomeRepository;
import com.timeline.api.infrastructure.repository.PostRepository;
import com.timeline.api.infrastructure.repository.TimelineRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class PostServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(PostServiceImplTest.class);

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private HomeRepository homeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FollowService followService;
    @Autowired
    private TimelineRepository timelineRepository;

    @Test
    public void 일_유닉스시간_변환_테스트() {
        long time = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH).getTime() / 1000;
        log.info(String.valueOf(time));
    }

    @Test
    public void 포스팅_테스트() {
        userService.signUpUser("tuguri4", "김길동", "asdf12");
        followService.followUser("tuguri4", "tuguri87");
        postService.savePost("tuguri87", "하이");
        assertThat(postRepository.findByYearMonth(getCurrentDateTimestamp()).isPresent()).isTrue();
        assertThat(homeRepository.findByUserId("tuguri87").isPresent()).isTrue();
        assertThat(timelineRepository.findByUserId("tuguri4").isPresent()).isTrue();
        postRepository.deleteAll();
        homeRepository.deleteAll();
        timelineRepository.deleteAll();
    }

    private String getCurrentDateTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYYMM");
        return LocalDate.now().format(formatter);
    }

}