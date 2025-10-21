package edu.nf.seckill.common.exception;

import lombok.Getter;

/**
 * @author wangl
 * @date 2022/5/17
 */
@Getter
public enum ErrorMessageEnum {
    /**
     * 商品售完
     */
    SELL_OUT(10001, "此商品已售完"),
    /**
     * 下单失败
     */
    PLACE_ORDER_FAIL(10002, "下单失败");

    private final int code;
    private final String message;

    ErrorMessageEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
