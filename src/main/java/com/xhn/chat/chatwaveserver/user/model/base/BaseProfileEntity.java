package com.xhn.chat.chatwaveserver.user.model.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 普通用户信息表，存储用户基础资料和状态
 * </p>
 *
 * @author xhn
 * @since 2025-09-15
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("user_profile")
public class BaseProfileEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户信息表主键，自增唯一
     */
    @TableId(value = "profile_id", type = IdType.AUTO)
    private Long profileId;

    /**
     * 用户ID，用于业务或账号关联
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

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
     * 性别: 0未知,1男,2女
     */
    private Short gender;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 地址
     */
    private String address;

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
