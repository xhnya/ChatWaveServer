package com.xhn.chat.chatwaveserver.WebSocket.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("messages")
public class Message {
    @Id
    private String id;
    private Long senderId;
    private Long receiverId; // 私聊对象，群聊时为 null
    private Long groupId;    // 群聊对象，私聊时为 null
    private String content;
    private String type; // text / image / emoji / voice
    private LocalDateTime timestamp;
    // ...getter/setter
}
