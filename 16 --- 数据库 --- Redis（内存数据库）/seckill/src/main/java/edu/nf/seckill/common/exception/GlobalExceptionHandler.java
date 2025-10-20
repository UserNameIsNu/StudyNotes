package edu.nf.seckill.common.exception;

import edu.nf.seckill.common.result.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wangl
 * @date 2024/11/26
 */
@RestControllerAdvice("edu.nf.seckill.web")
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResultVO handleGlobalException(GlobalException e) {
        log.error(e.getMessage(), e);
        return ResultVO.error(e.getErrorCode(),  e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResultVO.error(500,  "服务器内部错误");
    }
}