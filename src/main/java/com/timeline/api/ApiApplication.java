package com.timeline.api;

import com.timeline.api.domain.entity.Account;
import com.timeline.api.infrastructure.repository.AccountRepository;
import com.timeline.api.domain.entity.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableCaching
@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    CommandLineRunner bootstrapTestAccount(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Account account = new Account();
            account.setUserId("tuguri87");
            account.setPassword(passwordEncoder.encode("ahfk12"));
            account.setUserRole(UserRole.USER);
            account.setUserName("한승우");
            accountRepository.save(account);
        };
    }
}
