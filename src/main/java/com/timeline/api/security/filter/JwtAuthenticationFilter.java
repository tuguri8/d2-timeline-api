package com.timeline.api.security.filter;

import com.timeline.api.security.handlers.JwtAuthenticationFailureHandler;
import com.timeline.api.security.jwt.HeaderTokenExtractor;
import com.timeline.api.security.tokens.JwtPreProcessingToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private HeaderTokenExtractor headerTokenExtractor;


    protected JwtAuthenticationFilter(RequestMatcher matcher) {
        super(matcher);
    }

    public JwtAuthenticationFilter(RequestMatcher matcher, JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler, HeaderTokenExtractor headerTokenExtractor) {
        super(matcher);
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        this.headerTokenExtractor = headerTokenExtractor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws
                                                                                                 AuthenticationException,
                                                                                                 IOException,
                                                                                                 ServletException {
        String tokenPayload = req.getHeader("Authorization");
        JwtPreProcessingToken jwtPreProcessingToken = new JwtPreProcessingToken(this.headerTokenExtractor.extract(tokenPayload));
        return super.getAuthenticationManager().authenticate(jwtPreProcessingToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        this.unsuccessfulAuthentication(request, response, failed);
    }
}
