package com.xhn.chat.chatwaveserver.user.service;

import com.xhn.chat.chatwaveserver.user.model.model.LoginModel;
import com.xhn.chat.chatwaveserver.user.model.model.RegisterRequestModel;
import com.xhn.chat.chatwaveserver.user.model.UsersEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 93095
* @description 针对表【users(用户表)】的数据库操作Service
* @createDate 2025-04-01 10:28:31
*/
public interface UsersService extends IService<UsersEntity> {

    void register(RegisterRequestModel registerRequestModel);

    LoginModel login(LoginModel loginModel);

    void selectTest(String name);
}
