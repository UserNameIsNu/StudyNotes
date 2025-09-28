package com.example.ch12.common.exception;

import com.example.ch12.common.result.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.example.ch12.web")
public class GlobalExceptionAdvice {
    @ExceptionHandler(GlobalException.class)
    public ResultVO handleGlobalException(GlobalException globalException) {
        return ResultVO.error(globalException.getErrorCode(), globalException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception e) {
        return ResultVO.error(500, "服务器异常：" + e);
    }
}
