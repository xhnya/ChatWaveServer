package com.xhn.chat.chatwaveserver.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.chat.model.ChatListEntity;
import com.xhn.chat.chatwaveserver.chat.service.ChatListService;
import com.xhn.chat.chatwaveserver.chat.mapper.ChatListMapper;
import com.xhn.chat.chatwaveserver.user.model.FriendsEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 93095
* @description 针对表【chat_list(聊天列表表)】的数据库操作Service实现
* @createDate 2025-04-01 10:37:30
*/
@Service
public class ChatListServiceImpl extends ServiceImpl<ChatListMapper, ChatListEntity>
    implements ChatListService{

    @Override
    public List<ChatListEntity> getChatList(Long currentUsername) {
        QueryWrapper<ChatListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", currentUsername);
        queryWrapper.orderByDesc("updated_at"); // 按更新时间降序排列
        return list(queryWrapper);
    }

    @Override
    public boolean createChatList(FriendsEntity friendsEntity) {

        // 检查是否已经存在聊天列表
        QueryWrapper<ChatListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", friendsEntity.getUserId())
                    .eq("chat_id", friendsEntity.getFriendId());
        long count = count(queryWrapper);
        if (count > 0) {
            //更新时间
            ChatListEntity chatListEntity = getOne(queryWrapper);
            chatListEntity.setUpdatedAt(new Date());
        } else {
            // 创建新的聊天列表
            ChatListEntity chatListEntity = new ChatListEntity();
            chatListEntity.setUserId(friendsEntity.getUserId());
            chatListEntity.setChatId(friendsEntity.getFriendId());
            chatListEntity.setChatType("single");

            chatListEntity.setUpdatedAt(new Date());
            save(chatListEntity);
        }


        return true;
    }
}




