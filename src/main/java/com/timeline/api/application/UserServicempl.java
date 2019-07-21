package com.timeline.api.application;

import com.timeline.api.application.exception.UserServiceException;
import com.timeline.api.application.model.UserModel;
import com.timeline.api.domain.entity.Account;
import com.timeline.api.infrastructure.repository.AccountRepository;
import com.timeline.api.domain.entity.UserRole;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServicempl implements UserService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServicempl(AccountRepository accountRepository,
                          ModelMapper modelMapper,
                          PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel signUpUser(String userId, String userName, String password) {
        if (accountRepository.findByUserId(userId).isPresent()) {
            throw new UserServiceException.UserIdIsAlreadyInUseException();
        }
        Account account = new Account();
        account.setUserId(userId);
        account.setUserName(userName);
        account.setUserRole(UserRole.USER);
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
        return modelMapper.map(account, UserModel.class);
    }
}
