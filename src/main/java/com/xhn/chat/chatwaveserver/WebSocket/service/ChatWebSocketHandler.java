package com.xhn.chat.chatwaveserver.WebSocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhn.chat.chatwaveserver.WebSocket.model.Message;
import com.xhn.chat.chatwaveserver.chat.service.ChatGroupsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    private static final Map<Long, Set<WebSocketSession>> userSessions = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

    @Autowired private MessageService messageService;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ChatGroupsService groupService;
    @Autowired private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Long userId = getUserId(session);
        userSessions.computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet()).add(session);
        logger.info("用户 {} 连接", userId);
        stringRedisTemplate.opsForSet().add("onlineUsers", String.valueOf(userId));

        // 处理接收消息逻辑
        Mono<Void> input = session.receive()
                .flatMap(webSocketMessage -> {
                    String payload = webSocketMessage.getPayloadAsText();
                    try {
                        Message msg = objectMapper.readValue(payload, Message.class);
                        messageService.saveMessage(msg);

                        return dispatchMessage(msg);
                    } catch (Exception e) {
                        logger.error("处理消息失败", e);
                        return Mono.empty();
                    }
                }).then();

        // 处理关闭连接逻辑
        Mono<Void> close = session.closeStatus()
                .doOnTerminate(() -> removeSession(userId, session))
                .then();

        return Mono.zip(input, close).then();
    }

    private Mono<Void> dispatchMessage(Message msg) {
        try {
            String json = objectMapper.writeValueAsString(msg);
            WebSocketMessage webSocketMessage = null;

            if (msg.getGroupId() != null) {
                // 示例：List<Long> groupMembers = groupService.getMembers(msg.getGroupId());
                List<Long> groupMembers = new ArrayList<>();
                return Flux.fromIterable(groupMembers)
                        .flatMap(memberId -> {
                            Set<WebSocketSession> sessions = userSessions.get(memberId);
                            if (sessions != null) {
                                return Flux.fromIterable(sessions)
                                        .flatMap(s -> s.send(Mono.just(s.textMessage(json))));
                            }
                            return Mono.empty();
                        }).then();
            } else if (msg.getReceiverId() != null) {
                Set<WebSocketSession> sessions = userSessions.get(msg.getReceiverId());
                if (sessions != null) {
                    return Flux.fromIterable(sessions)
                            .flatMap(s -> s.send(Mono.just(s.textMessage(json))))
                            .then();
                }
            }
        } catch (Exception e) {
            logger.error("发送消息失败", e);
        }
        return Mono.empty();
    }

    public void sendSystemMessage(Long userId, String content, Map<String, Object> data) {
        Set<WebSocketSession> sessions = userSessions.get(userId);
        if (sessions != null && !sessions.isEmpty()) {
            Message msg = new Message();
            msg.setSenderId(0L);
            msg.setReceiverId(userId);
            msg.setType("system");
            msg.setSystemMessage(true);
            msg.setContent(content);
            msg.setData(data);

            messageService.saveMessage(msg);

            try {
                String json = objectMapper.writeValueAsString(msg);
                for (WebSocketSession session : sessions) {
                    session.send(Mono.just(session.textMessage(json))).subscribe();
                }
            } catch (Exception e) {
                logger.error("推送系统消息失败", e);
            }
        }
    }

    private void removeSession(Long userId, WebSocketSession session) {
        Set<WebSocketSession> sessions = userSessions.get(userId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                userSessions.remove(userId);
            }
        }
        stringRedisTemplate.opsForSet().remove("onlineUsers", String.valueOf(userId));
        logger.info("用户 {} 断开连接", userId);
    }

    private Long getUserId(WebSocketSession session) {
        String query = session.getHandshakeInfo().getUri().getQuery();
        return Long.valueOf(query.split("=")[1]);
    }

    public Set<WebSocketSession> getSessions(Long userId) {
        return userSessions.getOrDefault(userId, Collections.emptySet());
    }
}
