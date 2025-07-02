package com.xhn.chat.chatwaveserver.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhn.chat.chatwaveserver.base.exception.AppException;
import com.xhn.chat.chatwaveserver.user.model.FriendsEntity;
import com.xhn.chat.chatwaveserver.user.model.model.FriendGroupListModel;
import com.xhn.chat.chatwaveserver.user.service.FriendRequestsService;
import com.xhn.chat.chatwaveserver.user.service.FriendsService;
import com.xhn.chat.chatwaveserver.user.mapper.FriendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (friendsEntity.getStatus() != 1) {
            // 如果状态不是已添加，则抛出异常
            return;
        }
        //添加好友数据
        FriendsEntity friendsEntity1 = new FriendsEntity();
        friendsEntity1.setUserId(friendsEntity.getFriendId());
        friendsEntity1.setFriendId(friendsEntity.getUserId());
        friendsEntity1.setStatus(friendsEntity.getStatus()); // 设置状态为已添加
        //如果有这个数据，但是状态不是已添加，则更新状态
        QueryWrapper<FriendsEntity> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id", friendsEntity1.getUserId())
                     .eq("friend_id", friendsEntity1.getFriendId());
        FriendsEntity existingFriend = getOne(queryWrapper1);
        if (existingFriend != null && existingFriend.getStatus() != 1) {
            // 如果存在好友关系但状态不是已添加，则更新状态
            existingFriend.setStatus(friendsEntity.getStatus());
            updateById(existingFriend);
        }else {
            save(friendsEntity1); // 保存好友关系
        }
        FriendsEntity friendsEntity2 = new FriendsEntity();
        friendsEntity2.setUserId(friendsEntity.getUserId());
        friendsEntity2.setFriendId(friendsEntity.getFriendId());

        // 如果有这个数据，但是状态不是已添加，则更新状态
        QueryWrapper<FriendsEntity> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id", friendsEntity2.getUserId())
                     .eq("friend_id", friendsEntity2.getFriendId());
        FriendsEntity existingFriend2 = getOne(queryWrapper2);
        if (existingFriend2 != null && existingFriend2.getStatus() != 1) {
            // 如果存在好友关系但状态不是已添加，则更新状态
            existingFriend2.setStatus(friendsEntity.getStatus());
            updateById(existingFriend2);
        }else {
            friendsEntity2.setStatus(friendsEntity.getStatus()); // 设置状态为已添加
            save(friendsEntity2); // 保存好友关系
        }


    }

    @Override
    public List<FriendGroupListModel> getFriendsList(Long userId) {
        FriendGroupListModel friendGroupListModel = new FriendGroupListModel();
        friendGroupListModel.setName("我的好友");
        friendGroupListModel.setContacts(baseMapper.getFriendsByUserId(userId));

        return java.util.Collections.singletonList(friendGroupListModel);
    }
}




