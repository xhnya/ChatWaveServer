package com.xhn.chat.chatwaveserver.WebSocket.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document("messages")
public class Message {
    @Id
    private String id;

    private Long senderId; // 系统消息可为 null 或 0 或 -1 表示 system
    private Long receiverId; // 私聊、系统消息目标用户
    private Long groupId;    // 群聊消息时有值，私聊/系统消息为 null

    private String content;  // 消息内容

    /**
     * 消息类型: text / image / voice / emoji / system
     */
    private String type;

    /**
     * 是否系统消息
     */
    private boolean isSystemMessage = false;

    /**
     * 可选的附加数据（系统消息结构体化 action 等）
     */
    private Map<String, Object> data;

    private LocalDateTime timestamp = LocalDateTime.now();

    // 可选字段：状态、已读等
    private String status; // sent / delivered / read
}
