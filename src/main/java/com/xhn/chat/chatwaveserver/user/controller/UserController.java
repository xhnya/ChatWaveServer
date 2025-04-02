package com.xhn.chat.chatwaveserver.user.controller;


import com.xhn.chat.chatwaveserver.base.response.ResultResponse;
import com.xhn.chat.chatwaveserver.user.model.RegisterRequestModel;
import com.xhn.chat.chatwaveserver.user.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UsersService usersService;

    @PostMapping("/login")
    public ResultResponse login() {

        return ResultResponse.success();
    }

    @PostMapping("/register")
    public ResultResponse<String> register(@Valid @RequestBody  RegisterRequestModel registerRequestModel) {
        //  注册
        usersService.register(registerRequestModel);
        return ResultResponse.success("注册成功");
    }

    @GetMapping("/test")
    public ResultResponse<String> test() {
        usersService.list();
        return ResultResponse.success("测试成功");
    }




}
