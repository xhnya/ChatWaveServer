package com.xhn.chat.chatwaveserver.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户联系人表
 * @TableName user_contact
 */
@TableName(value ="user_contact")
@Data
public class UserContactEntity implements Serializable {
    /**
     * 联系人ID，唯一标识一条联系人记录
     */
    @TableId
    private String id;

    /**
     * 用户ID（当前用户），用于标识当前用户
     */
    private String userId;

    /**
     * 联系人ID（联系人用户），用于标识联系人
     */
    private String contactId;

    /**
     * 联系人昵称（用户自定义），可选字段
     */
    private String nickname;

    /**
     * 联系人电话，最多15个字符
     */
    private String phoneNumber;

    /**
     * 联系人邮箱，最多255个字符
     */
    private String email;

    /**
     * 联系人地址，最多255个字符
     */
    private String address;

    /**
     * 好友关系状态，默认值为 "Pending"（等待中）
     */
    private Object status;

    /**
     * 是否静音联系人，默认为否（FALSE）
     */
    private Integer isMuted;

    /**
     * 是否将联系人标记为常用，默认为否（FALSE）
     */
    private Integer isFavorite;

    /**
     * 备注，存储联系人相关的附加信息
     */
    private String notes;

    /**
     * 记录创建时间，默认为当前时间
     */
    private Date createdAt;

    /**
     * 最后更新时间，默认为当前时间，更新时自动修改
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
        UserContactEntity other = (UserContactEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getContactId() == null ? other.getContactId() == null : this.getContactId().equals(other.getContactId()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getPhoneNumber() == null ? other.getPhoneNumber() == null : this.getPhoneNumber().equals(other.getPhoneNumber()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIsMuted() == null ? other.getIsMuted() == null : this.getIsMuted().equals(other.getIsMuted()))
            && (this.getIsFavorite() == null ? other.getIsFavorite() == null : this.getIsFavorite().equals(other.getIsFavorite()))
            && (this.getNotes() == null ? other.getNotes() == null : this.getNotes().equals(other.getNotes()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getContactId() == null) ? 0 : getContactId().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getPhoneNumber() == null) ? 0 : getPhoneNumber().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsMuted() == null) ? 0 : getIsMuted().hashCode());
        result = prime * result + ((getIsFavorite() == null) ? 0 : getIsFavorite().hashCode());
        result = prime * result + ((getNotes() == null) ? 0 : getNotes().hashCode());
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
        sb.append(", contactId=").append(contactId);
        sb.append(", nickname=").append(nickname);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", email=").append(email);
        sb.append(", address=").append(address);
        sb.append(", status=").append(status);
        sb.append(", isMuted=").append(isMuted);
        sb.append(", isFavorite=").append(isFavorite);
        sb.append(", notes=").append(notes);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}