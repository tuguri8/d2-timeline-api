package com.timeline.api.interfaces;

import com.timeline.api.application.FollowService;
import com.timeline.api.interfaces.dto.request.FollowRequest;
import com.timeline.api.interfaces.dto.response.AcceptFriendsResponse;
import com.timeline.api.interfaces.dto.response.FollowUserResponse;
import com.timeline.api.security.tokens.PostAuthorizationToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService friendsService;

    public FollowController(FollowService friendsService) {this.friendsService = friendsService;}

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public FollowUserResponse followUser (@RequestBody FollowRequest addFollowRequest, Authentication authentication) {
        return friendsService.followUser(getUserNameFromAuthentication(authentication), addFollowRequest.getFollowId());
    }

    private String getUserNameFromAuthentication(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        return token.getAccountContext().getUsername();
    }

}
