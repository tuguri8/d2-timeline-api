package com.timeline.api.application.service;

import com.timeline.api.infrastructure.repository.HomeRepository;
import com.timeline.api.infrastructure.repository.PostRepository;
import com.timeline.api.infrastructure.repository.TimelineRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class TimelineServicemplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private HomeRepository homeRepository;
    @Autowired
    private FollowService followService;
    @Autowired
    private PostService postService;
    @Autowired
    private TimelineService timelineService;
    @Autowired
    private TimelineRepository timelineRepository;

    @Test
    public void 타임라인_조회_테스트() {
        userService.signUpUser("tuguri4", "김길동", "asdf12");
        followService.followUser("tuguri4", "tuguri87");
        postService.savePost("tuguri87", "하이");
        assertThat(timelineService.getTimelineList("tuguri4").size() > 0 ).isTrue();
        postRepository.deleteAll();
        homeRepository.deleteAll();
        timelineRepository.deleteAll();
    }

}