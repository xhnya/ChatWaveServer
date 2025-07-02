package com.xhn.chat.chatwaveserver.user.model.model;

import lombok.Data;

/**
 * @author xhn
 * @date 2025/7/2 14:44
 * @description
 */
@Data
public class FriendListModel {
    private String title;
    private String prependAvatar;
    private String subtitle;
    private Long userId;
}
