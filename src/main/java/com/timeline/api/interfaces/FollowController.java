package com.timeline.api.interfaces;

import com.timeline.api.application.service.FollowService;
import com.timeline.api.interfaces.dto.request.FollowRequest;
import com.timeline.api.interfaces.dto.response.FollowListResponse;
import com.timeline.api.interfaces.dto.response.FollowUserResponse;
import com.timeline.api.interfaces.dto.response.FollowerListResponse;
import com.timeline.api.interfaces.support.ExtractUserFromAuth;
import com.timeline.api.security.tokens.PostAuthorizationToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/list")
    public List<FollowListResponse> getFollowList(Authentication authentication) {
        return followService.getFollowList(ExtractUserFromAuth.getUserNameFromAuthentication(authentication));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/follower/list")
    public List<FollowerListResponse> getFollowerList(Authentication authentication) {
        return followService.getFollowerList(ExtractUserFromAuth.getUserNameFromAuthentication(authentication));
    }
    @PreAuthorize("hasRole('ROLE_USER')")

    @PostMapping
    public FollowUserResponse followUser(@RequestBody FollowRequest addFollowRequest, Authentication authentication) {
        return followService.followUser(ExtractUserFromAuth.getUserNameFromAuthentication(authentication), addFollowRequest.getFollowId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping
    public FollowUserResponse unFollowUser(@RequestBody FollowRequest unFollowRequest, Authentication authentication) {
        return followService.unFollowUser(ExtractUserFromAuth.getUserNameFromAuthentication(authentication), unFollowRequest.getFollowId());
    }

}
