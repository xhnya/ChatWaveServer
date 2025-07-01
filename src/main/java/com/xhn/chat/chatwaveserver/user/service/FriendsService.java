package com.xhn.chat.chatwaveserver.user.service;

import com.xhn.chat.chatwaveserver.user.model.FriendsEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 93095
* @description 针对表【friends(好友关系表)】的数据库操作Service
* @createDate 2025-04-01 10:28:31
*/
public interface FriendsService extends IService<FriendsEntity> {


    void acceptFriendRequest(FriendsEntity friendsEntity);
}
