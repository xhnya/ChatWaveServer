package com.xhn.chat.chatwaveserver.user.controller;


import cn.hutool.Hutool;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.xhn.chat.chatwaveserver.base.constant.RoleConstants;
import com.xhn.chat.chatwaveserver.base.response.ResultResponse;
import com.xhn.chat.chatwaveserver.user.model.BaseUserEntity;
import com.xhn.chat.chatwaveserver.user.model.LoginModel;
import com.xhn.chat.chatwaveserver.user.model.UserInfoModel;
import com.xhn.chat.chatwaveserver.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    private final JwtUtil JwtUtil = SpringUtil.getBean(JwtUtil.class);



    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;



    @PostMapping("/login")
    public ResultResponse<LoginModel> login(@RequestBody LoginModel loginModelRequest) {
        LoginModel loginModel = new LoginModel();
        loginModel.setUserName(loginModelRequest.getUserName());
        //设置角色 为正常用户

        String accessToken = JwtUtil.generateAccessToken(loginModelRequest.getUserName(), RoleConstants.ROLE_USER);
        loginModel.setAccessToken(accessToken);

        String md5Hex1 = DigestUtil.md5Hex(loginModelRequest.getUserName());

        String  refreshToken=JwtUtil.generateRefreshToken(md5Hex1);
        //设置过期时间 1000 * 60 * 60 * 24 * 7
        long expirationTime = 60 * 60 * 24 * 7; // 7天的毫秒数
        redisTemplate.opsForValue().set(md5Hex1, "xhn",expirationTime).subscribe();
        loginModel.setRefreshToken(refreshToken);
        log.info("登录成功");
        return ResultResponse.success(loginModel);
    }


    @PostMapping("/register")
    public ResultResponse<String> register(@RequestBody BaseUserEntity baseUserEntityRequest) {
        BaseUserEntity baseUserEntity = new BaseUserEntity();
        baseUserEntity.setUserName(baseUserEntityRequest.getUserName());
        String password = baseUserEntityRequest.getPassword();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(password);

        baseUserEntity.setPassword(encode);
        String userId = IdUtil.objectId();
        baseUserEntity.setUserId(userId);

        return ResultResponse.success("注册成功");
    }


//
//    @PostMapping("/referToken")
//    public ResultResponse<LoginModel>  referToken(@RequestBody UserInfoModel userInfoModelRequest, HttpServletRequest httpServletRequest) {
////        LoginModel user = new LoginModel();
////        //从请求头里面获取令牌
////
////        String refreshToken = httpServletRequest.getHeader("X-Refresh-Token");
////        //解析令牌
////        String token = jwtTokenUtil.extractUsername(refreshToken);
////        //从redis中获取
////        String value = redisTemplate.opsForValue().get(token).block();
////        if (value == null) {
////            return ResultResponse.error("令牌过期");
////        }
////        user.setUserName(value);
////        //生成令牌
////        String accessToken = jwtTokenUtil.generateAccessToken(value);
////        user.setAccessToken(accessToken);
////        user.setRefreshToken(refreshToken);
////
//
////        return ResultResponse.success(user);
//    }



    @PostMapping("/getUserInfo")
    public ResultResponse<UserInfoModel>  getUserInfo(@RequestBody UserInfoModel userInfoModelRequest) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserName("xhn");
        userInfoModel.setNickName("xhn");
        userInfoModel.setAge("18");
        userInfoModel.setAvatar("https://avatars.githubusercontent.com/u/37236788?v=4");
        return ResultResponse.success(userInfoModel);
    }


}
