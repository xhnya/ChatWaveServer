//package com.xhn.chat.chatwaveserver.WebSocket.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.xhn.chat.chatwaveserver.WebSocket.model.Message;
//import com.xhn.chat.chatwaveserver.chat.service.ChatGroupsService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class ChatWebSocketHandler extends TextWebSocketHandler {
//
//    private static final Map<Long, Set<WebSocketSession>> userSessions = new ConcurrentHashMap<>();
//    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);
//
//    @Autowired private MessageService messageService;
//    @Autowired private ObjectMapper objectMapper;
//    @Autowired private ChatGroupsService groupService;
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//
//
//
//
//    // ✅ 封装方法：推送系统消息
//    public void sendSystemMessage(Long userId, String content, Map<String, Object> data) {
//        Set<WebSocketSession> sessions = userSessions.get(userId);
//        if (sessions != null && !sessions.isEmpty()) {
//            Message msg = new Message();
//            msg.setSenderId(0L); // 表示系统
//            msg.setReceiverId(userId);
//            msg.setType("system");
//            msg.setSystemMessage(true);
//            msg.setContent(content);
//            msg.setData(data);
//            messageService.saveMessage(msg);
//            try {
//                String json = objectMapper.writeValueAsString(msg);
//                for (WebSocketSession session : sessions) {
//                    session.sendMessage(new TextMessage(json));
//                }
//            } catch (IOException e) {
//                logger.error("推送系统消息失败: {}", e.getMessage(), e);
//            }
//        }
//    }
//
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) {
//        Long userId = getUserId(session);
//        userSessions.computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet()).add(session);
//        logger.info("用户 {} 连接", userId);
//        stringRedisTemplate.opsForSet().add("onlineUsers", String.valueOf(userId));
////        stringRedisTemplate.opsForSet().add("userNodes:" + userId, currentNodeId);
//    }
//
//    @Override
//    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
//        try {
//            Message msg = objectMapper.readValue(textMessage.getPayload(), Message.class);
//            messageService.saveMessage(msg);
//
//            if (msg.getGroupId() != null) {
////                List<Long> groupMembers = groupService.getMembers(msg.getGroupId());
//                List<Long> groupMembers = new ArrayList<>();
//                for (Long memberId : groupMembers) {
//                    Set<WebSocketSession> sessions = userSessions.get(memberId);
//                    if (sessions != null) {
//                        for (WebSocketSession s : sessions) {
//                            try {
//                                s.sendMessage(new TextMessage(objectMapper.writeValueAsString(msg)));
//                            } catch (IOException e) {
//                                logger.error("群发消息失败，sessionId={}", s.getId(), e);
//                            }
//                        }
//                    }
//                }
//            } else if (msg.getReceiverId() != null) {
//                Set<WebSocketSession> sessions = userSessions.get(msg.getReceiverId());
//                if (sessions != null) {
//                    for (WebSocketSession s : sessions) {
//                        try {
//                            s.sendMessage(new TextMessage(objectMapper.writeValueAsString(msg)));
//                        } catch (IOException e) {
//                            logger.error("私聊消息发送失败，sessionId={}", s.getId(), e);
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.error("处理消息失败", e);
//        }
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
//        Long userId = getUserId(session);
//        Set<WebSocketSession> sessions = userSessions.get(userId);
//        if (sessions != null) {
//            sessions.remove(session);
//            if (sessions.isEmpty()) {
//                userSessions.remove(userId);
//            }
//        }
//        stringRedisTemplate.opsForSet().remove("onlineUsers", String.valueOf(userId));
//
//        logger.info("用户 {} 断开连接", userId);
//    }
//
//    private Long getUserId(WebSocketSession session) {
//        // 生产环境建议改为基于token鉴权获取
//        return Long.valueOf(Objects.requireNonNull(session.getUri()).getQuery().split("=")[1]);
//    }
//
//    public Set<WebSocketSession> getSessions(Long userId) {
//        return userSessions.getOrDefault(userId, Collections.emptySet());
//    }
//}
//
