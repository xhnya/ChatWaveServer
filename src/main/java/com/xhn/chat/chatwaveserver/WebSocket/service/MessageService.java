package com.xhn.chat.chatwaveserver.WebSocket.service;

import com.xhn.chat.chatwaveserver.WebSocket.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MongoTemplate mongoTemplate;



    public void saveMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        mongoTemplate.save(message);
    }

    public List<Message> getPrivateChat(Long userId1, Long userId2) {
        Query query = new Query(Criteria.where("senderId").in(userId1, userId2)
            .and("receiverId").in(userId1, userId2));
        return mongoTemplate.find(query, Message.class);
    }

    public List<Message> getGroupChat(Long groupId) {
        return mongoTemplate.find(Query.query(Criteria.where("groupId").is(groupId)), Message.class);
    }
}
