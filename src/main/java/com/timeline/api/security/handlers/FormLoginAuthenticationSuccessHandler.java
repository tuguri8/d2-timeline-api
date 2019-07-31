package com.timeline.api.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeline.api.security.AccountContext;
import com.timeline.api.security.dtos.TokenDto;
import com.timeline.api.security.jwt.JwtFactory;
import com.timeline.api.security.tokens.PostAuthorizationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtFactory jwtFactory;

    private final ObjectMapper objectMapper;

    public FormLoginAuthenticationSuccessHandler(JwtFactory jwtFactory, ObjectMapper objectMapper) {this.jwtFactory = jwtFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws
                                                                                                              IOException,
                                                                                                              ServletException {
        PostAuthorizationToken postAuthorizationToken = (PostAuthorizationToken) auth;
        AccountContext context = (AccountContext) postAuthorizationToken.getPrincipal();
        String tokenString = jwtFactory.generateToken(context);

        processResponse(res, writeDto(tokenString));
    }

    private TokenDto writeDto(String token) {
        return new TokenDto(token);
    }

    private void processResponse(HttpServletResponse res, TokenDto dto) throws IOException {
        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(objectMapper.writeValueAsString(dto));
    }
}
