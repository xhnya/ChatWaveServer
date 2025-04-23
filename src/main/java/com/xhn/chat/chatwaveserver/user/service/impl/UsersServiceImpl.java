package com.xhn.chat.chatwaveserver.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.base.exception.AppException;
import com.xhn.chat.chatwaveserver.base.exception.ErrorCode;
import com.xhn.chat.chatwaveserver.user.model.model.LoginModel;
import com.xhn.chat.chatwaveserver.user.model.model.RegisterRequestModel;
import com.xhn.chat.chatwaveserver.user.model.UsersEntity;
import com.xhn.chat.chatwaveserver.user.service.UsersService;
import com.xhn.chat.chatwaveserver.user.mapper.UsersMapper;
import com.xhn.chat.chatwaveserver.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



/**
* @author 93095
* @description 针对表【users(用户表)】的数据库操作Service实现
* @createDate 2025-04-01 10:28:31
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, UsersEntity>
    implements UsersService{



    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void register(RegisterRequestModel registerRequestModel) {
        String password = registerRequestModel.getPassword();
        //        加密存储
        String encodedPassword = passwordEncoder.encode(password); // Encrypt the password
//        QueryWrapper<UsersEntity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", registerRequestModel.getUsername());
        long usernameCount = count(new QueryWrapper<UsersEntity>().eq("username", registerRequestModel.getUsername()));
        if (usernameCount > 0) {
            throw new AppException("用户名已存在", ErrorCode.INTERNAL_SERVER_ERROR.getCode());
        }

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUsername(registerRequestModel.getUsername());
        usersEntity.setPassword(encodedPassword);
        usersEntity.setNickname(registerRequestModel.getUsername());
        usersEntity.setStatus(1);
        save(usersEntity);
    }

    @Override
    public LoginModel login(LoginModel loginModel) {
        String username = loginModel.getUsername();
        String password = loginModel.getPassword();
        // 查询用户
        UsersEntity user = getOne(new QueryWrapper<UsersEntity>().eq("username", username));
        if (user == null) {
            throw new AppException("用户不存在", ErrorCode.USER_NOT_FOUND.getCode());
        }
        // 验证密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AppException("密码错误", ErrorCode.USER_PASSWORD_ERROR.getCode());
        }
        // 登录成功，返回用户信息
        loginModel.setId(user.getId());
        String accessToken = jwtUtil.generateAccessToken(user.getUsername(), "user");

        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
        loginModel.setAccessToken(accessToken);
        loginModel.setRefreshToken(refreshToken);


        return loginModel;
    }

    @Override
    public void selectTest(String name) {
        baseMapper.selectTest(name);
    }
}




