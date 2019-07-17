package com.timeline.api.interfaces;

import com.timeline.api.application.FriendsService;
import com.timeline.api.interfaces.dto.request.AddFriendsRequest;
import com.timeline.api.interfaces.dto.response.AddFriendsResponse;
import com.timeline.api.security.tokens.PostAuthorizationToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friends")
public class FriendsController {

    private final FriendsService friendsService;

    public FriendsController(FriendsService friendsService) {this.friendsService = friendsService;}

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public AddFriendsResponse addFriends(@RequestBody AddFriendsRequest addFriendsRequest, Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
//        return token.getAccountContext().getUsername();
        return new AddFriendsResponse(friendsService.addFriends(token.getAccountContext().getUsername(), addFriendsRequest.getId()));
    }
}
