package com.xhn.chat.chatwaveserver.user.request;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LoginRequest {
    private String username;
    private String password; // 明文密码，由后端用 PasswordEncoder 校验
}

