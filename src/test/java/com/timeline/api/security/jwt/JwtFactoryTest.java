package com.timeline.api.security.jwt;

import com.timeline.api.domain.entity.Account;
import com.timeline.api.domain.entity.UserRole;
import com.timeline.api.security.AccountContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtFactoryTest {

    public JwtFactoryTest() {
    }

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    @Autowired
    private JwtFactory jwtFactory;

    private AccountContext context;


    @Before
    public void setUp() {
        Account account = new Account();
        account.setUserId("tuguri8");
        account.setPassword("ahfk12");
        account.setUserRole(UserRole.USER);
        this.context = AccountContext.fromAccountModel(account);
    }

    @Test
    public void TEST_JWT_GENERATE() {
        log.error(jwtFactory.generateToken(this.context));
    }
}