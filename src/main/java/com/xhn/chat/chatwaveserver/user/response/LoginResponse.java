package com.xhn.chat.chatwaveserver.user.response;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String username;
}

