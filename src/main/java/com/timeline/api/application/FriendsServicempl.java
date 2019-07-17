package com.timeline.api.application;

import com.timeline.api.application.model.AddFriendsModel;
import com.timeline.api.domain.Account;
import com.timeline.api.domain.AccountRepository;
import com.timeline.api.domain.Friends;
import com.timeline.api.domain.FriendsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FriendsServicempl implements FriendsService{

    private final AccountRepository accountRepository;
    private final FriendsRepository friendsRepository;
    private final ModelMapper modelMapper;

    public FriendsServicempl(AccountRepository accountRepository,
                             FriendsRepository friendsRepository,
                             ModelMapper modelMapper) {this.accountRepository = accountRepository;
        this.friendsRepository = friendsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AddFriendsModel addFriends(String userAccountId, Long friendId) {
        Account user = accountRepository.findByUserId(userAccountId).orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        Account friend = accountRepository.findById(friendId).orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        Friends friends = new Friends();
        friends.setUser(user);
        friends.setFriend(friend);
        friends.setActive(false);
        friendsRepository.save(friends);
        return modelMapper.map(friends, AddFriendsModel.class);
    }
}
