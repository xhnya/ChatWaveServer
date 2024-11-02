package com.xhn.chat.chatwaveserver.base.response;

import lombok.Data;

@Data
public class ResultResponse<T> {

    private int status;        // 状态码，例如 200 表示成功
    private String message;    // 返回信息，例如 "Success" 或 "Error"
    private T data;            // 返回的数据内容

    // 构造方法
    public ResultResponse() {}

    public ResultResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // 工厂方法便于快速创建响应对象
    public static <T> ResultResponse<T> success(T data) {
        return new ResultResponse<>(200, "Success", data);
    }

    public static <T> ResultResponse<T> success(String message, T data) {
        return new ResultResponse<>(200, message, data);
    }

    public static <T> ResultResponse<T> error(int status, String message) {
        return new ResultResponse<>(status, message, null);
    }

    public static <T> ResultResponse<T> error(String message) {
        return new ResultResponse<>(400, message, null);
    }


}