package com.xhn.chat.chatwaveserver.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.user.model.UsersEntity;
import com.xhn.chat.chatwaveserver.user.service.UsersService;
import com.xhn.chat.chatwaveserver.user.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author 93095
* @description 针对表【users(用户表)】的数据库操作Service实现
* @createDate 2025-04-01 10:28:31
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, UsersEntity>
    implements UsersService{

}




