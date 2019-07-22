package com.timeline.api.application.service;

import com.timeline.api.interfaces.dto.response.TimelineResponse;

import java.util.List;

public interface TimelineService {
    List<TimelineResponse> getTimelineList(String userId);
}
