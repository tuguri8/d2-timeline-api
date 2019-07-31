package com.timeline.api.security.tokens;

import com.timeline.api.security.dtos.FormLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private PreAuthorizationToken(String userName, String password) {
        super(userName, password);
    }

    public PreAuthorizationToken(FormLoginDto dto) {
        super(dto.getUserId(), dto.getPassword());
    }

    public String getuserName() {
        return (String) super.getPrincipal();
    }

    public String getPassword() {
        return (String) super.getCredentials();
    }
}
