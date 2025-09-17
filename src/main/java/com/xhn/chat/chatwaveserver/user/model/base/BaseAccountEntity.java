package com.xhn.chat.chatwaveserver.user.model.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户账号表，存储用户登录信息和状态
 * </p>
 *
 * @author xhn
 * @since 2025-09-15
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("user_account")
public class BaseAccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID，自增唯一
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码哈希
     */
    private String passwordHash;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户状态: 1=正常, 0=禁用
     */
    private Short status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 逻辑删除时间
     */
    private LocalDateTime deletedTime;
}
