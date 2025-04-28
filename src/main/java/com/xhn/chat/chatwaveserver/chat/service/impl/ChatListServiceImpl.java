package com.xhn.chat.chatwaveserver.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.chat.model.ChatListEntity;
import com.xhn.chat.chatwaveserver.chat.service.ChatListService;
import com.xhn.chat.chatwaveserver.chat.mapper.ChatListMapper;
import org.springframework.stereotype.Service;

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
        return list(queryWrapper);
    }
}




