package com.xhn.chat.chatwaveserver.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.user.model.UserInfoEntity;
import com.xhn.chat.chatwaveserver.user.service.UserInfoService;
import com.xhn.chat.chatwaveserver.user.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 93095
* @description 针对表【user_info(用户信息表)】的数据库操作Service实现
* @createDate 2024-11-15 15:28:51
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity>
    implements UserInfoService{

}




