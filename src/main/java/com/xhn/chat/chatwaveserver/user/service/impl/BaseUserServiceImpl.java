package com.xhn.chat.chatwaveserver.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.user.model.BaseUserEntity;
import com.xhn.chat.chatwaveserver.user.service.BaseUserService;
import com.xhn.chat.chatwaveserver.user.mapper.BaseUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 93095
* @description 针对表【base_user】的数据库操作Service实现
* @createDate 2024-11-12 17:40:24
*/
@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUserEntity>
    implements BaseUserService{

}




