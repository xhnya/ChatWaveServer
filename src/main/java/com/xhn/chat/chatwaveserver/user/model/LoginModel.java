package com.xhn.chat.chatwaveserver.user.model;

import lombok.Data;

@Data
public class LoginModel {

    private String userName;

    private String accessToken;

    private String refreshToken;


}
