package com.xhn.chat.chatwaveserver.base.exception;


import com.xhn.chat.chatwaveserver.base.response.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultResponse<String>> handleValidationException(MethodArgumentNotValidException ex) {
        // 提取第一个字段错误的消息
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("参数校验失败");

        logger.warn("参数校验异常: {}", errorMessage);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResultResponse.error(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }


    // 处理自定义业务异常
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResultResponse<String>> handleBusinessException(AppException ex) {
        // 获取对应的 ErrorCode 枚举
        ErrorCode errorCode = ErrorCode.fromCode(ex.getStatus());
        logger.error("业务异常: 状态码={}, 信息={}", ex.getStatus(), ex.getMessage(), ex);  // 最后一个参数 ex 会打印堆栈跟踪

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ResultResponse.error(ex.getStatus(), ex.getMessage()));
    }



    // 处理特定的非法参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResultResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("非法参数异常===>>>@ExceptionHandler(IllegalArgumentException.class):信息={}", ex.getMessage(), ex);  // 最后一个参数 ex 会打印堆栈跟踪
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResultResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }


    // 处理所有未处理的异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultResponse<String>> handleGeneralException(Exception ex) {
        // 可以在这里记录日志
        logger.error("异常===>>>@ExceptionHandler(Exception.class):信息={}", ex.getMessage(), ex);  // 最后一个参数 ex 会打印堆栈跟踪

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)  // 显式指定 JSON 类型
                .body(ResultResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }


}
