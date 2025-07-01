package com.xhn.chat.chatwaveserver.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.base.exception.AppException;
import com.xhn.chat.chatwaveserver.user.model.FriendsEntity;
import com.xhn.chat.chatwaveserver.user.service.FriendRequestsService;
import com.xhn.chat.chatwaveserver.user.service.FriendsService;
import com.xhn.chat.chatwaveserver.user.mapper.FriendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 93095
* @description 针对表【friends(好友关系表)】的数据库操作Service实现
* @createDate 2025-04-01 10:28:31
*/
@Service
public class FriendsServiceImpl extends ServiceImpl<FriendsMapper, FriendsEntity>
    implements FriendsService{

    @Autowired
    FriendRequestsService friendRequestsService;


    @Override
    public void acceptFriendRequest(FriendsEntity friendsEntity) {
        //查询是否存在好友了

        QueryWrapper<FriendsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", friendsEntity.getUserId())
                    .eq("friend_id", friendsEntity.getFriendId());
        long count = count(queryWrapper);
        if (count > 0) {
            // 如果已经存在好友关系，则不需要再次添加
           throw new AppException("已经是好友关系，无需重复添加");
        }
        // 如果不存在好友关系，则添加好友
        friendRequestsService.updateFriendRequest(friendsEntity);
        //添加好友数据
        FriendsEntity friendsEntity1 = new FriendsEntity();
        friendsEntity1.setUserId(friendsEntity.getFriendId());
        friendsEntity1.setFriendId(friendsEntity.getUserId());
        friendsEntity1.setStatus(1); // 设置状态为已添加
        save(friendsEntity1); // 保存好友关系
        FriendsEntity friendsEntity2 = new FriendsEntity();
        friendsEntity2.setUserId(friendsEntity.getUserId());
        friendsEntity2.setFriendId(friendsEntity.getFriendId());
        friendsEntity2.setStatus(1); // 设置状态为已添加
        // 保存好友关系
        save(friendsEntity2);

    }
}




