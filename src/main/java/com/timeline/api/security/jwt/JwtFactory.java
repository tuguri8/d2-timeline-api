package com.timeline.api.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.timeline.api.security.AccountContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtFactory {

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);
    // TODO key값 암호화
    private static String signingKey = "jwttest";

    public String generateToken(AccountContext context) {
        String token = null;
        try {
            token = JWT.create()
                       .withIssuer("hsw")
                       .withClaim("USERNAME", context.getUsername())
                       .withClaim("USER_ROLE", context.getAccount().getUserRole().getRoleName())
                       .sign(generateAlgorithm());
        } catch (Exception e){
            log.error(e.getMessage());
        }

        return token;
    }

    private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(signingKey);
    }
}
