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
import com.xhn.chat.chatwaveserver.user.service.BaseUserService;
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
    private BaseUserService baseUserService;

    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;



    @PostMapping("/login")
    public ResultResponse<LoginModel> login(@RequestBody LoginModel loginModelRequest) {
        //判断userName 是否存在


        LoginModel loginModel = baseUserService.login(loginModelRequest);


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



    @PostMapping("/referToken")
    public ResultResponse<LoginModel>  referToken(@RequestBody UserInfoModel userInfoModelRequest, HttpServletRequest httpServletRequest) {

        //从请求头里面获取令牌

        String refreshToken = httpServletRequest.getHeader("X-Refresh-Token");

        LoginModel user=  baseUserService.referToken(refreshToken);



        return ResultResponse.success(user);
    }



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
