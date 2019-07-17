package com.timeline.api.application;

import com.timeline.api.application.model.AddFriendsModel;

public interface FriendsService {
    AddFriendsModel addFriends(String userAccountId, Long friendId);
}
