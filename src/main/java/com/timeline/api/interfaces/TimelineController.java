package com.timeline.api.interfaces;

import com.timeline.api.application.service.TimelineService;
import com.timeline.api.interfaces.dto.response.TimelineResponse;
import com.timeline.api.interfaces.support.ExtractUserFromAuth;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/timeline")
public class TimelineController {

    private final TimelineService timelineService;

    public TimelineController(TimelineService timelineService) {this.timelineService = timelineService;}

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<TimelineResponse> getTimelineList(Authentication authentication) {
        return timelineService.getTimelineList(ExtractUserFromAuth.getUserNameFromAuthentication(authentication));
    }
}
