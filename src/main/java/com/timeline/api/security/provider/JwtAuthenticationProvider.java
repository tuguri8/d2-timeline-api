package com.timeline.api.security.provider;

import com.timeline.api.security.AccountContext;
import com.timeline.api.security.jwt.JwtDecoder;
import com.timeline.api.security.tokens.JwtPreProcessingToken;
import com.timeline.api.security.tokens.PostAuthorizationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtDecoder jwtDecoder;

    public JwtAuthenticationProvider(JwtDecoder jwtDecoder) {this.jwtDecoder = jwtDecoder;}

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        AccountContext context = jwtDecoder.decodeJwt(token);
        return PostAuthorizationToken.getTokenFromAccountContext(context);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreProcessingToken.class.isAssignableFrom(authentication);
    }
}
