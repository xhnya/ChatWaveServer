package com.xhn.chat.chatwaveserver.chat.controller;


import com.xhn.chat.chatwaveserver.base.response.ResultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatIndexController {


    @GetMapping("/test")
    public ResultResponse<String> chatTest() {
        return ResultResponse.success("chat test");
    }

}
