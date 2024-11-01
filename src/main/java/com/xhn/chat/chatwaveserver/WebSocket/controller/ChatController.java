package com.xhn.chat.chatwaveserver.WebSocket.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {



    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public String sendMessage(String message) {
        // 处理接收到的消息
        return message; // 返回的消息将发送到所有订阅了/topic/messages的客户端
    }
}
