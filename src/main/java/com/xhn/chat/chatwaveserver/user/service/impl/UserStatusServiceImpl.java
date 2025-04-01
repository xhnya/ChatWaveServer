package com.xhn.chat.chatwaveserver.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.user.model.UserStatusEntity;
import com.xhn.chat.chatwaveserver.user.service.UserStatusService;
import com.xhn.chat.chatwaveserver.user.mapper.UserStatusMapper;
import org.springframework.stereotype.Service;

/**
* @author 93095
* @description 针对表【user_status(用户状态表)】的数据库操作Service实现
* @createDate 2025-04-01 10:28:31
*/
@Service
public class UserStatusServiceImpl extends ServiceImpl<UserStatusMapper, UserStatusEntity>
    implements UserStatusService{

}




