package com.xhn.chat.chatwaveserver.chat.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ChatListEntity other = (ChatListEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getChatId() == null ? other.getChatId() == null : this.getChatId().equals(other.getChatId()))
            && (this.getChatType() == null ? other.getChatType() == null : this.getChatType().equals(other.getChatType()))
            && (this.getLastMessage() == null ? other.getLastMessage() == null : this.getLastMessage().equals(other.getLastMessage()))
            && (this.getLastMessageTime() == null ? other.getLastMessageTime() == null : this.getLastMessageTime().equals(other.getLastMessageTime()))
            && (this.getUnreadCount() == null ? other.getUnreadCount() == null : this.getUnreadCount().equals(other.getUnreadCount()))
            && (this.getIsPinned() == null ? other.getIsPinned() == null : this.getIsPinned().equals(other.getIsPinned()))
            && (this.getIsMuted() == null ? other.getIsMuted() == null : this.getIsMuted().equals(other.getIsMuted()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getChatId() == null) ? 0 : getChatId().hashCode());
        result = prime * result + ((getChatType() == null) ? 0 : getChatType().hashCode());
        result = prime * result + ((getLastMessage() == null) ? 0 : getLastMessage().hashCode());
        result = prime * result + ((getLastMessageTime() == null) ? 0 : getLastMessageTime().hashCode());
        result = prime * result + ((getUnreadCount() == null) ? 0 : getUnreadCount().hashCode());
        result = prime * result + ((getIsPinned() == null) ? 0 : getIsPinned().hashCode());
        result = prime * result + ((getIsMuted() == null) ? 0 : getIsMuted().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", chatId=").append(chatId);
        sb.append(", chatType=").append(chatType);
        sb.append(", lastMessage=").append(lastMessage);
        sb.append(", lastMessageTime=").append(lastMessageTime);
        sb.append(", unreadCount=").append(unreadCount);
        sb.append(", isPinned=").append(isPinned);
        sb.append(", isMuted=").append(isMuted);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}