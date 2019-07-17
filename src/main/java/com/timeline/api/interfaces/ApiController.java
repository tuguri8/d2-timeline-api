package com.timeline.api.interfaces;

import com.timeline.api.security.tokens.PostAuthorizationToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api")
//public class ApiController {
//
//    @PreAuthorize("hasRole('ROLE_USER')")
//    @GetMapping("/hello")
//    public String getUserName(Authentication authentication) {
//        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
//        return token.getAccountContext().getUsername();
//    }
//}
