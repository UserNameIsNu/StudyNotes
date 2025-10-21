package edu.nf.seckill.common.exception;

import lombok.Getter;

/**
 * @author wangl
 * @date 2024/11/26
 */
@Getter
public class GlobalException extends RuntimeException {

    private final Integer errorCode;

    public GlobalException(ErrorMessageEnum errorEnum) {
        super(errorEnum.getMessage());
        this.errorCode = errorEnum.getCode();
    }
}