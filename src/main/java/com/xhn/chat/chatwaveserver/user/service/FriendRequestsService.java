package com.xhn.chat.chatwaveserver.user.service;

import com.xhn.chat.chatwaveserver.user.model.FriendRequestsEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhn.chat.chatwaveserver.user.model.FriendsEntity;
import com.xhn.chat.chatwaveserver.user.model.model.FriendRequestModel;

import java.util.List;

/**
* @author 93095
* @description 针对表【friend_requests(好友请求表)】的数据库操作Service
* @createDate 2025-04-01 10:28:31
*/
public interface FriendRequestsService extends IService<FriendRequestsEntity> {

    void addFriendRequest(Long userId, Long friendId);

    List<FriendRequestModel> getFriendRequests(Long userId);

    void updateFriendRequest(FriendsEntity friendsEntity);
}
