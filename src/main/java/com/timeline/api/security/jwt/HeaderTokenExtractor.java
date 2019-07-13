package com.timeline.api.security.jwt;

import com.timeline.api.security.InvalidJwtException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class HeaderTokenExtractor {
    public static final String HEADER_PREFIX = "Bearer ";

    public String extract(String header) {
        if(StringUtils.isEmpty(header) | header.length() < HEADER_PREFIX.length()) {
            throw new InvalidJwtException("올바른 토큰 값이 아닙니다.");
        }

        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}
