package com.xhn.chat.chatwaveserver.WebSocket.service;

import com.xhn.chat.chatwaveserver.WebSocket.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class MessageService {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    // 保存消息，返回保存后的 Mono<Message>
    public Mono<Message> saveMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        return mongoTemplate.save(message);
    }

    // 获取两个用户的私聊消息，返回 Flux<Message>
    public Flux<Message> getPrivateChat(Long userId1, Long userId2) {
        Query query = new Query(Criteria.where("senderId").in(userId1, userId2)
                .and("receiverId").in(userId1, userId2));
        return mongoTemplate.find(query, Message.class);
    }

    // 获取群聊消息，返回 Flux<Message>
    public Flux<Message> getGroupChat(Long groupId) {
        Query query = Query.query(Criteria.where("groupId").is(groupId));
        return mongoTemplate.find(query, Message.class);
    }
}
