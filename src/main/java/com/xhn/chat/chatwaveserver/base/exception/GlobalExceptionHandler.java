package com.xhn.chat.chatwaveserver.base.exception;


import com.xhn.chat.chatwaveserver.base.response.ResultResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {



    // 处理自定义业务异常
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResultResponse<String>> handleBusinessException(AppException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ResultResponse.error(ex.getStatus(), ex.getMessage()));
    }

    // 处理所有未处理的异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultResponse<String>> handleGeneralException(Exception ex) {
        // 可以在这里记录日志
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)  // 显式指定 JSON 类型
                .body(ResultResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }

    // 处理特定的非法参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResultResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResultResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }





}
