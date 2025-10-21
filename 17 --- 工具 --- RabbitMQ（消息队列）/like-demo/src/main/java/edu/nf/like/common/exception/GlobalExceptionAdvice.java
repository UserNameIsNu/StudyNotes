package edu.nf.like.common.exception;

import edu.nf.like.common.result.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wangl
 * @date 2023/12/6
 */
@RestControllerAdvice(basePackages = "edu.nf.like.web.controller")
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(LoginException.class)
    public ResultData<?> handleLoginException(LoginException e) {
        log.error(e.getMessage(), e);
        ResultData<?> vo = new ResultData<>();
        vo.setCode(e.getErrorCode());
        vo.setMessage(e.getMessage());
        return vo;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultData<?> handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        ResultData<?> vo = new ResultData<>();
        vo.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        vo.setMessage("服务器异常，稍后重试");
        return vo;
    }
}