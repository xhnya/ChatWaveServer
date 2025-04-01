package com.xhn.chat.chatwaveserver.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户状态表
 * @TableName user_status
 */
@TableName(value ="user_status")
@Data
public class UserStatusEntity implements Serializable {
    /**
     * 状态ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 状态消息
     */
    private String statusMessage;

    /**
     * 最后活跃时间
     */
    private Date lastActive;

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
        UserStatusEntity other = (UserStatusEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getStatusMessage() == null ? other.getStatusMessage() == null : this.getStatusMessage().equals(other.getStatusMessage()))
            && (this.getLastActive() == null ? other.getLastActive() == null : this.getLastActive().equals(other.getLastActive()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getStatusMessage() == null) ? 0 : getStatusMessage().hashCode());
        result = prime * result + ((getLastActive() == null) ? 0 : getLastActive().hashCode());
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
        sb.append(", statusMessage=").append(statusMessage);
        sb.append(", lastActive=").append(lastActive);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}