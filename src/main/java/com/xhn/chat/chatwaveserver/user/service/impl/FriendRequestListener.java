package com.xhn.chat.chatwaveserver.user.service.impl;

import com.xhn.chat.chatwaveserver.WebSocket.service.ChatWebSocketHandler;
import com.xhn.chat.chatwaveserver.user.model.FriendRequestsEntity;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Set;


@Component
public class FriendRequestListener {


    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Autowired
    ChatWebSocketHandler chatWebSocketHandler;

    @RabbitListener(queues = "friendRequestQueue")
    public void handleFriendRequest(FriendRequestsEntity event) {
        Long receiverId = event.getReceiverId();

        if (isUserOnline(receiverId)) {
//            webSocketService.pushNotification(receiverId, "您有新的好友请求");
            //获取连接
           chatWebSocketHandler.sendSystemMessage(receiverId, "您有新的好友请求", null);
            // 发送 WebSocket 消息
        } else {
//            offlineNotificationService.saveNotification(receiverId, "您有新的好友请求");
        }
    }

    private boolean isUserOnline(Long userId) {
        // 具体判断逻辑，如查询在线用户缓存
        Boolean isMember = stringRedisTemplate.opsForSet().isMember("onlineUsers", String.valueOf(userId));
        return Boolean.TRUE.equals(isMember);
    }
}
