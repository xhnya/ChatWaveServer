package com.xhn.chat.chatwaveserver.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.chat.model.MessagesEntity;
import com.xhn.chat.chatwaveserver.chat.service.MessagesService;
import com.xhn.chat.chatwaveserver.chat.mapper.MessagesMapper;
import org.springframework.stereotype.Service;

/**
* @author 93095
* @description 针对表【messages(聊天消息表)】的数据库操作Service实现
* @createDate 2025-04-01 10:37:30
*/
@Service
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, MessagesEntity>
    implements MessagesService{

}




