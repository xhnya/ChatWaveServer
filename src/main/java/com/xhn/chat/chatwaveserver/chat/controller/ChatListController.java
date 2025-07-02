package com.xhn.chat.chatwaveserver.chat.controller;

import com.xhn.chat.chatwaveserver.base.response.ResultResponse;
import com.xhn.chat.chatwaveserver.chat.model.ChatListEntity;
import com.xhn.chat.chatwaveserver.chat.service.ChatListService;
import com.xhn.chat.chatwaveserver.user.model.FriendsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xhn
 * @date 2025/4/25 10:33
 * @description
 */
@RestController
@RequestMapping("/chatlist")
public class ChatListController {

    @Autowired
    private ChatListService chatListService;


    @GetMapping("/getChatList")
    public ResultResponse<List<ChatListEntity>> getChatList(@RequestParam Long userId) {
        //获取token的用户id
        List<ChatListEntity> result= chatListService.getChatList(userId);
        return ResultResponse.success(result);
    }

    //创建一个聊天列表
    @PostMapping("/createChatList")
    public ResultResponse<String> createChatList(@RequestBody FriendsEntity friendsEntity) {
        return chatListService.createChatList(friendsEntity)
                ? ResultResponse.success("创建聊天列表成功")
                : ResultResponse.error("创建聊天列表失败");
    }


}
