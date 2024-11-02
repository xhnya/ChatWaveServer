package com.xhn.chat.chatwaveserver.base.exception;


import com.xhn.chat.chatwaveserver.base.response.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResultResponse<String>> handleBusinessException(AppException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ResultResponse.error(ex.getMessage()));
    }


    // 处理所有未处理的异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultResponse<String>> handleGeneralException(Exception ex) {
        // 可以在这里记录日志
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResultResponse.error("服务错误: " + ex.getMessage()));
    }

    // 处理特定的非法参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResultResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResultResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }





}
