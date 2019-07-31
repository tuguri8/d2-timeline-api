package com.timeline.api.application.service;

import com.timeline.api.application.exception.ServiceException;
import com.timeline.api.application.model.UserModel;
import com.timeline.api.domain.entity.Account;
import com.timeline.api.infrastructure.repository.AccountRepository;
import com.timeline.api.domain.entity.UserRole;
import com.timeline.api.interfaces.dto.response.UserListResponse;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
            throw new ServiceException.UserIdIsAlreadyInUseException();
        }
        Account account = new Account();
        account.setUserId(userId);
        account.setUserName(userName);
        account.setUserRole(UserRole.USER);
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
        return modelMapper.map(account, UserModel.class);
    }

    @Override
    public List<UserListResponse> getUserList(String name) {
        List<Account> userList = accountRepository.findByUserName(name).orElse(Collections.emptyList());
        return userList.stream()
                       .map(user -> modelMapper.map(user, UserListResponse.class))
                       .collect(Collectors.toList());
    }
}
