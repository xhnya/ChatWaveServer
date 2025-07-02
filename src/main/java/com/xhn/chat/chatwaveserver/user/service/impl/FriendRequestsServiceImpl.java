package com.xhn.chat.chatwaveserver.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.user.model.FriendRequestsEntity;
import com.xhn.chat.chatwaveserver.user.model.FriendsEntity;
import com.xhn.chat.chatwaveserver.user.model.model.FriendRequestModel;
import com.xhn.chat.chatwaveserver.user.service.FriendRequestsService;
import com.xhn.chat.chatwaveserver.user.mapper.FriendRequestsMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 93095
* @description 针对表【friend_requests(好友请求表)】的数据库操作Service实现
* @createDate 2025-04-01 10:28:31
*/
@Service
public class FriendRequestsServiceImpl extends ServiceImpl<FriendRequestsMapper, FriendRequestsEntity>
    implements FriendRequestsService{


    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void addFriendRequest(Long userId, Long friendId) {

        FriendRequestsEntity friendRequest = new FriendRequestsEntity();
        friendRequest.setSenderId(userId);
        friendRequest.setReceiverId(friendId);
        friendRequest.setStatus(0); // 设置请求状态为待处理
        // 设置其他必要的字段
        save(friendRequest); // 保存好友请求

        // 构造事件消息对象

        // 发送事件到 RabbitMQ
        rabbitTemplate.convertAndSend("friendRequestExchange", "friend.request", friendRequest);
    }

    @Override
    public List<FriendRequestModel> getFriendRequests(Long userId) {
        return baseMapper.getFriendRequestsByUserId(userId);
    }

    @Override
    public void updateFriendRequest(FriendsEntity friendsEntity) {
        baseMapper.updateFriendRequestStatus(friendsEntity.getUserId(), friendsEntity.getFriendId(),friendsEntity.getStatus());
    }
}




