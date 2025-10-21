package edu.nf.seckill.service;

/**
 * @author wangl
 * @date 2024/11/27
 */
public enum GoodsEnum {
    /**
     * 缓存商品的key前缀
     */
    KEY_PREFIX("goods:");

    private final String prefix;

    GoodsEnum(String prefix) {
        this.prefix = prefix;
    }

    public String value() {
        return prefix;
    }
}