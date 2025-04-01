package com.xhn.chat.chatwaveserver.chat.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 聊天设置表
 * @TableName chat_settings
 */
@TableName(value ="chat_settings")
@Data
public class ChatSettingsEntity implements Serializable {
    /**
     * 设置ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 聊天ID（单聊或群聊）
     */
    private Long chatId;

    /**
     * 聊天类型
     */
    private Object chatType;

    /**
     * 是否静音
     */
    private Integer isMuted;

    /**
     * 是否置顶
     */
    private Integer isPinned;

    /**
     * 自定义昵称
     */
    private String customNickname;

    /**
     * 聊天背景图片
     */
    private String backgroundImage;

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
        ChatSettingsEntity other = (ChatSettingsEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getChatId() == null ? other.getChatId() == null : this.getChatId().equals(other.getChatId()))
            && (this.getChatType() == null ? other.getChatType() == null : this.getChatType().equals(other.getChatType()))
            && (this.getIsMuted() == null ? other.getIsMuted() == null : this.getIsMuted().equals(other.getIsMuted()))
            && (this.getIsPinned() == null ? other.getIsPinned() == null : this.getIsPinned().equals(other.getIsPinned()))
            && (this.getCustomNickname() == null ? other.getCustomNickname() == null : this.getCustomNickname().equals(other.getCustomNickname()))
            && (this.getBackgroundImage() == null ? other.getBackgroundImage() == null : this.getBackgroundImage().equals(other.getBackgroundImage()))
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
        result = prime * result + ((getIsMuted() == null) ? 0 : getIsMuted().hashCode());
        result = prime * result + ((getIsPinned() == null) ? 0 : getIsPinned().hashCode());
        result = prime * result + ((getCustomNickname() == null) ? 0 : getCustomNickname().hashCode());
        result = prime * result + ((getBackgroundImage() == null) ? 0 : getBackgroundImage().hashCode());
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
        sb.append(", isMuted=").append(isMuted);
        sb.append(", isPinned=").append(isPinned);
        sb.append(", customNickname=").append(customNickname);
        sb.append(", backgroundImage=").append(backgroundImage);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}