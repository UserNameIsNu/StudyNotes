package edu.nf.like.common.result;

import lombok.Data;

/**
 * @author wangl
 * @date 2023/12/6
 */
@Data
public class ResultData<T> {
    private Integer code;
    private String message;
    private T data;
}