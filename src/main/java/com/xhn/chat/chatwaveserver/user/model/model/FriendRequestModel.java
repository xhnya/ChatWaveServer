package com.xhn.chat.chatwaveserver.user.model.model;

import lombok.Data;

import java.util.Date;

/**
 * @author xhn
 * @date 2025/7/1 15:27
 * @description
 */
@Data
public class FriendRequestModel {

    private String nickname;
    private Long userId;
    private Integer status;
    private Date createdAt;
}
