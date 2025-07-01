package com.xhn.chat.chatwaveserver.user.mapper;

import com.xhn.chat.chatwaveserver.user.model.FriendRequestsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhn.chat.chatwaveserver.user.model.model.FriendRequestModel;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 93095
* @description 针对表【friend_requests(好友请求表)】的数据库操作Mapper
* @createDate 2025-04-01 10:28:31
* @Entity com.xhn.chat.chatwaveserver.user.model.FriendRequestsEntity
*/
public interface FriendRequestsMapper extends BaseMapper<FriendRequestsEntity> {

    List<FriendRequestModel> getFriendRequestsByUserId(Long userId);

    @Update("UPDATE friend_requests SET status = 1 WHERE sender_id = #{friendId} AND receiver_id = #{userId}")
    void updateFriendRequestStatus(Long userId, Long friendId);
}




