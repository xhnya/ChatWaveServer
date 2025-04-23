package com.xhn.chat.chatwaveserver.user.controller;


import com.xhn.chat.chatwaveserver.base.response.ResultResponse;
import com.xhn.chat.chatwaveserver.user.model.model.LoginModel;
import com.xhn.chat.chatwaveserver.user.model.model.RegisterRequestModel;
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
    public ResultResponse<LoginModel> login(@Valid  @RequestBody LoginModel loginModel) {

        LoginModel result= usersService.login(loginModel);
        return ResultResponse.success(result);
    }

    @PostMapping("/register")
    public ResultResponse<String> register(@Valid @RequestBody  RegisterRequestModel registerRequestModel) {
        //  注册
        usersService.register(registerRequestModel);
        return ResultResponse.success("注册成功");
    }

    @GetMapping("/test")
    public ResultResponse<String> test(@RequestParam String name) {
        usersService.selectTest(name);
        return ResultResponse.success("测试成功");
    }

    @GetMapping("/getUserInfo")
    public ResultResponse<String> getUserInfo() {
        usersService.list();
        return ResultResponse.success("测试成功");
    }




}
