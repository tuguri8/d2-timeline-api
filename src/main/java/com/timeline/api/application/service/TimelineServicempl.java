package com.timeline.api.application.service;

import com.timeline.api.domain.entity.Post;
import com.timeline.api.domain.entity.Timeline;
import com.timeline.api.infrastructure.repository.PostRepository;
import com.timeline.api.infrastructure.repository.TimelineRepository;
import com.timeline.api.interfaces.dto.response.TimelineResponse;
import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TimelineServicempl implements TimelineService {

    private final TimelineRepository timelineRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public TimelineServicempl(TimelineRepository timelineRepository,
                              PostRepository postRepository, ModelMapper modelMapper) {
        this.timelineRepository = timelineRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TimelineResponse> getTimelineList(String userId) {
        List<Timeline> timelineList = timelineRepository.findByUserId(userId).orElse(Collections.emptyList());
        // 타임라인 테이블에서 포스트 ID 조회
        List<UUID> postIdList = timelineList.stream()
                                            .map(Timeline::getPostId)
                                            .collect(Collectors.toList());
        // 포스트 조회
        List<Post> postList = postRepository.findByYearMonthAndPostIdIn(getNowYearMonth(), postIdList)
                                            .orElse(Collections.emptyList());
        return postList.stream()
                       .map(post -> modelMapper.map(post, TimelineResponse.class))
                       .collect(Collectors.toList());
    }

    // 현재 yearMonth 조회
    private String getNowYearMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYYMM");
        return LocalDate.now().format(formatter);
    }

}
