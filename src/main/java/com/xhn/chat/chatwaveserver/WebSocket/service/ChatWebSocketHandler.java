package com.xhn.chat.chatwaveserver.WebSocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhn.chat.chatwaveserver.WebSocket.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Map<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

    @Autowired
    private MessageService messageService;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long userId = getUserId(session);
        logger.info("用户 {} 连接", userId);
        userSessions.put(userId, session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException {
        Message msg = objectMapper.readValue(textMessage.getPayload(), Message.class);
        messageService.saveMessage(msg);

        if (msg.getGroupId() != null) {
            // 下面直接群发给所有在线成员
            for (WebSocketSession s : userSessions.values()) {
                s.sendMessage(new TextMessage(objectMapper.writeValueAsString(msg)));
            }
        } else if (msg.getReceiverId() != null) {
            WebSocketSession receiverSession = userSessions.get(msg.getReceiverId());
            if (receiverSession != null) {
                receiverSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(msg)));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        userSessions.values().remove(session);
    }

    private Long getUserId(WebSocketSession session) {
        // 简化处理：你实际可用 token 解析或查询
        return Long.valueOf(Objects.requireNonNull(session.getUri()).getQuery().split("=")[1]);
    }
}
