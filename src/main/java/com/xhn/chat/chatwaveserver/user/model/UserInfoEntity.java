package com.xhn.chat.chatwaveserver.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户信息表
 * @TableName user_info
 */
@TableName(value ="user_info")
@Data
public class UserInfoEntity implements Serializable {
    /**
     * id
     */
    @TableId
    private String infoId;

    /**
     * 用户表的id
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickName;


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
        UserInfoEntity other = (UserInfoEntity) that;
        return (this.getInfoId() == null ? other.getInfoId() == null : this.getInfoId().equals(other.getInfoId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInfoId() == null) ? 0 : getInfoId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", infoId=").append(infoId);
        sb.append(", userId=").append(userId);
        sb.append(", nickName=").append(nickName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}