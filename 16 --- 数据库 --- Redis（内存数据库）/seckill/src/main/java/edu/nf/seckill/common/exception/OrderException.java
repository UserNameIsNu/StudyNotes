package edu.nf.seckill.common.exception;

/**
 * @author wangl
 * @date 2024/11/26
 */
public class OrderException extends GlobalException {

    public OrderException(ErrorMessageEnum errorEnum) {
        super(errorEnum);
    }
}