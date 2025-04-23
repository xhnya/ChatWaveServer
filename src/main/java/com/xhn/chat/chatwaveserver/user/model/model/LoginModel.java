package com.xhn.chat.chatwaveserver.user.model.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginModel {


    /**
     * 用户ID
     */
    private Long id;

    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    private String accessToken;

    private String refreshToken;
}
