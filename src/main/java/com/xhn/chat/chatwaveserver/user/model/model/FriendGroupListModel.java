package com.xhn.chat.chatwaveserver.user.model.model;

import lombok.Data;

import java.util.List;

/**
 * @author xhn
 * @date 2025/7/2 14:45
 * @description
 */
@Data
public class FriendGroupListModel {
    private String name;
    private List<FriendListModel> contacts;
}
