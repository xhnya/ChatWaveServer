package com.xhn.chat.chatwaveserver.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatHandler implements WebSocketHandler {
    private static final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private static final Map<String, Set<String>> groups = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        // 从 URL 获取 userId，如 ws://localhost:8080/chat?userId=A
        String userId = Optional.ofNullable(session.getHandshakeInfo().getUri().getQuery())
                .map(q -> q.split("=")[1]).orElse("unknown");
        userSessions.put(userId, session);
        System.out.println("用户上线：" + userId);

        // 处理收到的消息
        Flux<WebSocketMessage> output = session.receive()
                .flatMap(msg -> {
                    try {
                        Map<String, String> payload = mapper.readValue(msg.getPayloadAsText(), Map.class);
                        String type = payload.get("type");
                        String from = payload.get("from");
                        String content = payload.get("content");

                        if ("private".equals(type)) {
                            String to = payload.get("to");
                            WebSocketSession target = userSessions.get(to);
                            if (target != null && target.isOpen()) {
                                return target.send(Mono.just(
                                        target.textMessage(from + " : " + content)))
                                        .then(Mono.empty());
                            }
                        } else if ("group".equals(type)) {
                            String groupId = payload.get("groupId");
                            groups.putIfAbsent(groupId, new HashSet<>());
                            groups.get(groupId).add(from);

                            return Flux.fromIterable(groups.get(groupId))
                                    .filter(member -> !member.equals(from))
                                    .flatMap(member -> {
                                        WebSocketSession target = userSessions.get(member);
                                        if (target != null && target.isOpen()) {
                                            return target.send(Mono.just(
                                                    target.textMessage("[群 " + groupId + "] " + from + " : " + content)))
                                                    .then(Mono.empty());
                                        }
                                        return Mono.empty();
                                    }).then(Mono.empty());
                        }
                    } catch (Exception e) {
                        return Mono.empty();
                    }
                    return Mono.empty();
                })
                .thenMany(Flux.never()); // 不返回给当前用户消息

        return session.send(output)
                .doFinally(signal -> userSessions.remove(userId));
    }
}
