package com.xhn.chat.chatwaveserver.user.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.base.constant.ExceptionConstants;
import com.xhn.chat.chatwaveserver.base.constant.RoleConstants;
import com.xhn.chat.chatwaveserver.base.exception.AppException;
import com.xhn.chat.chatwaveserver.base.exception.GlobalExceptionHandler;
import com.xhn.chat.chatwaveserver.user.model.BaseUserEntity;
import com.xhn.chat.chatwaveserver.user.model.LoginModel;
import com.xhn.chat.chatwaveserver.user.service.BaseUserService;
import com.xhn.chat.chatwaveserver.user.mapper.BaseUserMapper;
import com.xhn.chat.chatwaveserver.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
* @author 93095
* @description 针对表【base_user】的数据库操作Service实现
* @createDate 2024-11-12 17:40:24
*/
@Service
@Slf4j
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUserEntity>
    implements BaseUserService{

    private final JwtUtil jwtUtil = SpringUtil.getBean(JwtUtil.class);


    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;

    @Override
    public LoginModel login(LoginModel loginModelRequest) {
        LoginModel loginModel=new LoginModel();
        //通过用户名查询数据
       BaseUserEntity userEntity= baseMapper.selectUserByUserName(loginModelRequest.getUserName());
       //验证密码
        if (userEntity==null){
            log.error("用户不存在");
           throw new AppException("用户不存在", ExceptionConstants.USER_NOT_FOUND);
        }
        //验证密码
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(loginModelRequest.getPassword(), userEntity.getPassword());
        if (!matches){
            log.error("密码错误");
            throw new AppException("密码错误", ExceptionConstants.USER_PASSWORD_ERROR);
        }

        String userName = loginModelRequest.getUserName();
        loginModel.setUserName(userName);
        //设置角色 为正常用户

        String accessToken = jwtUtil.generateAccessToken(userName, RoleConstants.ROLE_USER);
        loginModel.setAccessToken(accessToken);

       // String md5Hex1 = DigestUtil.md5Hex(loginModelRequest.getUserName());

        String  refreshToken=jwtUtil.generateRefreshToken(userName);
        //设置过期时间 1000 * 60 * 60 * 24 * 7
        long expirationTime = 60 * 60 * 24 * 7; // 7天的毫秒数
        redisTemplate.opsForValue().set(userName, RoleConstants.ROLE_USER,expirationTime).subscribe();
        loginModel.setRefreshToken(refreshToken);
        return loginModel;
    }

    @Override
    public LoginModel referToken(String refreshToken) {
        LoginModel loginModel=new LoginModel();
        //判断token是否有效
        if (!jwtUtil.validateJwtToken(refreshToken)){
            log.error("refreshToken 无效");
            throw new AppException("refreshToken 无效", ExceptionConstants.REFRESH_TOKEN_INVALID);
        }
        //判断redis中是否存在
        String userName = jwtUtil.getUsernameFromJwtToken(refreshToken);
        String role = redisTemplate.opsForValue().get(userName).block();
        if (role==null){
            log.error("refreshToken 无效");
            throw new AppException("refreshToken 无效", ExceptionConstants.REFRESH_TOKEN_INVALID);
        }
        //生成新的token
        String accessToken = jwtUtil.generateAccessToken(userName, role);
        loginModel.setAccessToken(accessToken);


        return loginModel;
    }
}




