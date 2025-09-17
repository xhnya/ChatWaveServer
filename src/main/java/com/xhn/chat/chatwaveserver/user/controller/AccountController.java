package com.xhn.chat.chatwaveserver.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xhn.chat.chatwaveserver.base.response.ResultResponse;
import com.xhn.chat.chatwaveserver.user.model.base.BaseAccountEntity;
import com.xhn.chat.chatwaveserver.user.request.LoginRequest;
import com.xhn.chat.chatwaveserver.user.response.LoginResponse;
import com.xhn.chat.chatwaveserver.user.service.IAccountService;
import com.xhn.chat.chatwaveserver.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

/**
 * 用户账号控制器（响应式登录）
 */
@RestController
@RequestMapping("/user/account")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/test")
    public Mono<ResultResponse> test() {
        return Mono.just(ResultResponse.success("Test successful"));
    }


    @PostMapping("/login")
    public Mono<ResultResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return Mono.just(ResultResponse.error(400, "用户名或密码不能为空"));
        }

        String username = loginRequest.getUsername();
        String rawPassword = loginRequest.getPassword();

        return Mono.fromCallable(() -> accountService.getOne(new QueryWrapper<BaseAccountEntity>().eq("username", username)))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(user -> {
                    if (user == null) {
                        return Mono.just(ResultResponse.error(401, "用户名或密码错误"));
                    }

                    if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
                        return Mono.just(ResultResponse.error(401, "用户名或密码错误"));
                    }

                    if (user.getStatus() != null && user.getStatus() == 0) {
                        return Mono.just(ResultResponse.error(403, "用户已禁用"));
                    }

                    String accessToken = jwtUtil.generateAccessToken(user.getUsername(), "USER");
                    String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

                    LoginResponse data = new LoginResponse();
                    data.setAccessToken(accessToken);
                    data.setRefreshToken(refreshToken);
                    data.setUserId(user.getUserId());
                    data.setUsername(user.getUsername());

                    // 异步更新最后登录时间
                    Mono.fromRunnable(() -> {
                        user.setLastLoginTime(LocalDateTime.now());
                        accountService.updateById(user);
                    }).subscribeOn(Schedulers.boundedElastic()).subscribe();

                    return Mono.just(ResultResponse.success(data));
                });
    }
}
