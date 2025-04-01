package com.xhn.chat.chatwaveserver.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.chat.model.MessageStatusEntity;
import com.xhn.chat.chatwaveserver.chat.service.MessageStatusService;
import com.xhn.chat.chatwaveserver.chat.mapper.MessageStatusMapper;
import org.springframework.stereotype.Service;

/**
* @author 93095
* @description 针对表【message_status(消息状态表)】的数据库操作Service实现
* @createDate 2025-04-01 10:37:30
*/
@Service
public class MessageStatusServiceImpl extends ServiceImpl<MessageStatusMapper, MessageStatusEntity>
    implements MessageStatusService{

}




