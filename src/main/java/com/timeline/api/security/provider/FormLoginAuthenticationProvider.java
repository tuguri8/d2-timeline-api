package com.timeline.api.security.provider;

import com.timeline.api.domain.Account;
import com.timeline.api.domain.AccountRepository;
import com.timeline.api.security.AccountContext;
import com.timeline.api.security.tokens.PostAuthorizationToken;
import com.timeline.api.security.tokens.PreAuthorizationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public FormLoginAuthenticationProvider(
        AccountRepository accountRepository,
        PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthorizationToken preAuthorizationToken = (PreAuthorizationToken) authentication;
        String username = preAuthorizationToken.getuserName();
        String password = preAuthorizationToken.getPassword();

        Account account = accountRepository.findByUserId(username).orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));

        if (isCorrectPassword(password, account)) {
            return PostAuthorizationToken.getTokenFromAccountContext(AccountContext.fromAccountModel(account));
        }

        throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    public boolean isCorrectPassword(String password, Account account) {
        return passwordEncoder.matches(password, account.getPassword());
    }
}
