//package com.xhn.chat.chatwaveserver.WebSocket.config;
//
//import com.xhn.chat.chatwaveserver.WebSocket.service.ChatWebSocketHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//    @Autowired
//    private ChatWebSocketHandler chatHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(chatHandler, "/ws/chat").setAllowedOrigins("*");
//    }
//}
