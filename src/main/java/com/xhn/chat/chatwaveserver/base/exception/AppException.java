package com.xhn.chat.chatwaveserver.base.exception;

import lombok.Getter;

@Getter
public class AppException  extends RuntimeException{
    private int status;

    public AppException(String message, int status) {
        super(message);
        this.status = status;
    }

}
