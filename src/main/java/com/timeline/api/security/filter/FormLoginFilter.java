package com.timeline.api.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeline.api.security.dtos.FormLoginDto;
import com.timeline.api.security.tokens.PreAuthorizationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;

    public FormLoginFilter(String defaultUrl,
                           AuthenticationSuccessHandler authenticationSuccessHandler,
                           AuthenticationFailureHandler authenticationFailureHandler) {
        super(defaultUrl);
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    protected FormLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws
                                                                                                 AuthenticationException,
                                                                                                 IOException,
                                                                                                 ServletException {
        FormLoginDto dto = new ObjectMapper().readValue(req.getReader(), FormLoginDto.class);
        PreAuthorizationToken preAuthorizationToken = new PreAuthorizationToken(dto);
        return super.getAuthenticationManager().authenticate(preAuthorizationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        AuthenticationFailureHandler handler = (req, res, exception) -> {
            Logger log = LoggerFactory.getLogger("authentication_failure");
            log.error(exception.getMessage());
        };

        handler.onAuthenticationFailure(request, response, failed);
    }


}
