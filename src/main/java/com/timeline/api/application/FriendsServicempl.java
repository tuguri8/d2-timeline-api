package com.timeline.api.application;

import com.timeline.api.application.model.AcceptFriendsModel;
import com.timeline.api.application.model.AddFriendsModel;
import com.timeline.api.domain.Account;
import com.timeline.api.domain.AccountRepository;
import com.timeline.api.domain.Friends;
import com.timeline.api.domain.FriendsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FriendsServicempl implements FriendsService {

    private final AccountRepository accountRepository;
    private final FriendsRepository friendsRepository;
    private final ModelMapper modelMapper;

    public FriendsServicempl(AccountRepository accountRepository,
                             FriendsRepository friendsRepository,
                             ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
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

    @Override
    public AcceptFriendsModel acceptFriends(String userAccountId, Long friendRequestId) {
        Friends friends = friendsRepository.findById(friendRequestId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 친구 요청입니다."));
        // 본인한테 온 친구 요청이 아닐 경우
        if (!isSameUser(userAccountId, friends)) {
            throw new RuntimeException("잘못된 접근입니다.");
        }
        friends.setActive(true);
        friendsRepository.save(friends);
        return modelMapper.map(friends, AcceptFriendsModel.class);
    }

    private Boolean isSameUser(String userAccountId, Friends friends) {
        Account user = accountRepository.findByUserId(userAccountId).orElseThrow(() -> new NoSuchElementException("아이디가 없습니다."));
        return user.getId().equals(friends.getFriend().getId());
    }
}
