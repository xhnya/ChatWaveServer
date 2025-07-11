package com.xhn.chat.chatwaveserver.chat.service;

import com.xhn.chat.chatwaveserver.chat.model.ChatListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhn.chat.chatwaveserver.user.model.FriendsEntity;

import java.util.List;

/**
* @author 93095
* @description 针对表【chat_list(聊天列表表)】的数据库操作Service
* @createDate 2025-04-01 10:37:30
*/
public interface ChatListService extends IService<ChatListEntity> {

    List<ChatListEntity> getChatList(Long currentUsername);

    boolean createChatList(FriendsEntity friendsEntity);
}
