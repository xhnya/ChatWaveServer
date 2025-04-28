package com.xhn.chat.chatwaveserver.chat.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import lombok.Data;

/**
 * 聊天列表表
 * @TableName chat_list
 */
@TableName(value ="chat_list")
@Data
public class ChatListEntity implements Serializable {
    /**
     * 聊天列表ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 聊天ID（可指向单聊或群聊）
     */
    private Long chatId;

    /**
     * 聊天类型：single=单聊，group=群聊
     */
    private Object chatType;

    /**
     * 最后一条消息内容
     */
    private String lastMessage;

    /**
     * 最后一条消息时间
     */
    private Date lastMessageTime;

    /**
     * 未读消息数
     */
    private Integer unreadCount;

    /**
     * 是否置顶
     */
    private Integer isPinned;

    /**
     * 是否静音
     */
    private Integer isMuted;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;


    private String avatar;

    private int seqNo;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "ChatListEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", chatId=" + chatId +
                ", chatType=" + chatType +
                ", lastMessage='" + lastMessage + '\'' +
                ", lastMessageTime=" + lastMessageTime +
                ", unreadCount=" + unreadCount +
                ", isPinned=" + isPinned +
                ", isMuted=" + isMuted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", avatar='" + avatar + '\'' +
                ", seqNo=" + seqNo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatListEntity that = (ChatListEntity) o;
        return seqNo == that.seqNo && Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(chatId, that.chatId) && Objects.equals(chatType, that.chatType) && Objects.equals(lastMessage, that.lastMessage) && Objects.equals(lastMessageTime, that.lastMessageTime) && Objects.equals(unreadCount, that.unreadCount) && Objects.equals(isPinned, that.isPinned) && Objects.equals(isMuted, that.isMuted) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(avatar, that.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, chatId, chatType, lastMessage, lastMessageTime, unreadCount, isPinned, isMuted, createdAt, updatedAt, avatar, seqNo);
    }
}