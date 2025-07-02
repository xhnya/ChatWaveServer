package com.xhn.chat.chatwaveserver.user.mapper;

import com.xhn.chat.chatwaveserver.user.model.FriendsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhn.chat.chatwaveserver.user.model.model.FriendListModel;

import java.util.List;

/**
* @author 93095
* @description 针对表【friends(好友关系表)】的数据库操作Mapper
* @createDate 2025-04-01 10:28:31
* @Entity com.xhn.chat.chatwaveserver.user.model.FriendsEntity
*/
public interface FriendsMapper extends BaseMapper<FriendsEntity> {

    List<FriendListModel> getFriendsByUserId(Long userId);
}




