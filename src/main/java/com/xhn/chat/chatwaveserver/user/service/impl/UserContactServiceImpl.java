package com.xhn.chat.chatwaveserver.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.user.model.UserContactEntity;
import com.xhn.chat.chatwaveserver.user.service.UserContactService;
import com.xhn.chat.chatwaveserver.user.mapper.UserContactMapper;
import org.springframework.stereotype.Service;

/**
* @author 93095
* @description 针对表【user_contact(用户联系人表)】的数据库操作Service实现
* @createDate 2024-11-15 15:28:51
*/
@Service
public class UserContactServiceImpl extends ServiceImpl<UserContactMapper, UserContactEntity>
    implements UserContactService{

}




