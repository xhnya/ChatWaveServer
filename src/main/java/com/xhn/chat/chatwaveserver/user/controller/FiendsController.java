package com.xhn.chat.chatwaveserver.user.controller;

import com.xhn.chat.chatwaveserver.base.response.ResultResponse;
import com.xhn.chat.chatwaveserver.user.model.FriendsEntity;
import com.xhn.chat.chatwaveserver.user.model.model.FriendRequestModel;
import com.xhn.chat.chatwaveserver.user.service.FriendRequestsService;
import com.xhn.chat.chatwaveserver.user.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xhn
 * @date 2025/6/10 15:50
 * @description
 */
@RestController
@RequestMapping("/fiends")
public class FiendsController {


    @Autowired
    FriendsService friendsService;

    @Autowired
    FriendRequestsService friendRequestsService;


//    添加好友
    @PostMapping("/addFriendRequest")
    public ResultResponse<String> addFriendRequest(@RequestParam Long userId, @RequestParam Long friendId) {
        // 获取token的用户id
        // 调用服务层方法添加好友
        // 返回结果
        if (userId == null || friendId == null) {
            return ResultResponse.error("User ID and Friend ID 不能为空");
        }
        if (userId.equals(friendId)) {
            return ResultResponse.error("不能添加自己为好友");
        }

        friendRequestsService.addFriendRequest(userId, friendId);
        return ResultResponse.success("Friend added successfully");
    }
    @GetMapping("/getFriendsRequestList")
    public ResultResponse<List<FriendRequestModel>> getFriendsRequestList(@RequestParam Long userId) {
        // 获取token的用户id
        // 调用服务层方法获取好友列表
        // 返回结果
        List<FriendRequestModel> requests = friendRequestsService.getFriendRequests(userId);
        return ResultResponse.success("获取好友请求列表成功", requests);

    }


    @PostMapping("/acceptFriendRequest")
    public ResultResponse<String> acceptFriendRequest(@RequestBody FriendsEntity friendsEntity) {
        // 获取token的用户id
        // 调用服务层方法接受好友请求
        // 返回结果
        Long userId = friendsEntity.getUserId();
        Long friendId = friendsEntity.getFriendId();
        if (userId == null || friendId == null) {
            return ResultResponse.error("User ID and Friend ID 不能为空");
        }

        friendsService.acceptFriendRequest(friendsEntity);
        return ResultResponse.success("成功接受好友请求");
    }


}
