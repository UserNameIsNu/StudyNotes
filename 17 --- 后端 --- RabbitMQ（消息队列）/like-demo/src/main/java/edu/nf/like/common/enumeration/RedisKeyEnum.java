package edu.nf.like.common.enumeration;

/**
 * @author wangl
 * @date 2023/12/7
 */
public enum RedisKeyEnum {
    POST_LIKE_PREFIX("post:like:");

    private String value;

    RedisKeyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}