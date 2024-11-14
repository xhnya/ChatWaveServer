package com.xhn.chat.chatwaveserver.user.controller;


import cn.hutool.Hutool;
import cn.hutool.crypto.digest.DigestUtil;
import com.xhn.chat.chatwaveserver.base.response.ResultResponse;
import com.xhn.chat.chatwaveserver.user.model.LoginModel;
import com.xhn.chat.chatwaveserver.user.model.UserInfoModel;
import com.xhn.chat.chatwaveserver.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;

    public UserController(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResultResponse<LoginModel> login(@RequestBody LoginModel loginModelRequest) {
        LoginModel loginModel = new LoginModel();
        loginModel.setUserName(loginModelRequest.getUserName());
        String accessToken = jwtTokenUtil.generateAccessToken(loginModelRequest.getUserName());
        loginModel.setAccessToken(accessToken);

        String md5Hex1 = DigestUtil.md5Hex(loginModelRequest.getUserName());

        String  refreshToken=jwtTokenUtil.generateRefreshToken(md5Hex1);
        //设置过期时间 1000 * 60 * 60 * 24 * 7
        long expirationTime = 60 * 60 * 24 * 7; // 7天的毫秒数
        redisTemplate.opsForValue().set(md5Hex1, "xhn",expirationTime).subscribe();
        loginModel.setRefreshToken(refreshToken);
        System.out.println("loginModel = " + loginModel);
        return ResultResponse.success(loginModel);
    }

    @PostMapping("/referToken")
    public ResultResponse<LoginModel>  referToken(@RequestBody UserInfoModel userInfoModelRequest, HttpServletRequest httpServletRequest) {
        LoginModel user = new LoginModel();
        //从请求头里面获取令牌

        String refreshToken = httpServletRequest.getHeader("X-Refresh-Token");
        //解析令牌
        String token = jwtTokenUtil.extractUsername(refreshToken);
        //从redis中获取
        String value = redisTemplate.opsForValue().get(token).block();
        if (value == null) {
            return ResultResponse.error("令牌过期");
        }
        user.setUserName(value);
        //生成令牌
        String accessToken = jwtTokenUtil.generateAccessToken(value);
        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshToken);


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