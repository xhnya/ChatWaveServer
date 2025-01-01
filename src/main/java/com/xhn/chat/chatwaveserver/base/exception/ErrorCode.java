package com.xhn.chat.chatwaveserver.base.exception;

import org.springframework.http.HttpStatus;


public enum ErrorCode {


    USER_NOT_FOUND(1001, HttpStatus.NOT_FOUND),           // 用户不存在 -> 404
    USER_PASSWORD_ERROR(1002, HttpStatus.UNAUTHORIZED),  // 密码错误 -> 401
    REFRESH_TOKEN_INVALID(1003, HttpStatus.FORBIDDEN),   // 刷新令牌无效 -> 403
    INTERNAL_SERVER_ERROR(1004, HttpStatus.INTERNAL_SERVER_ERROR);  // 其他未知错误 -> 500

    private final int code;
    private final HttpStatus httpStatus;

    // 构造函数
    ErrorCode(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    // 根据错误码查找对应的枚举
    public static ErrorCode fromCode(int code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        return INTERNAL_SERVER_ERROR;  // 默认返回一个 500 错误
    }


}
