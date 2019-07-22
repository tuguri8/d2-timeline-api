package com.timeline.api.interfaces.support;

import com.timeline.api.security.tokens.PostAuthorizationToken;
import org.springframework.security.core.Authentication;

public class ExtractUserFromAuth {
    public static String getUserNameFromAuthentication(Authentication authentication) {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;
        return token.getAccountContext().getUsername();
    }
}
