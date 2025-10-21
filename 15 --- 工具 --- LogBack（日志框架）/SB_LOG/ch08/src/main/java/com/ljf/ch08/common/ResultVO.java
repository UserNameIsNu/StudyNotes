package com.ljf.ch08.common;

import lombok.Data;

@Data
public class ResultVO<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResultVO<T> success(T data) {
        ResultVO<T> vo = new ResultVO<>();
        vo.setCode(200);
        vo.setData(data);
        return vo;
    }

    public static <T> ResultVO<T> success() {
        ResultVO<T> vo = new ResultVO<>();
        vo.setCode(200);
        return vo;
    }

    public static <T> ResultVO<T> error(int code, String message) {
        ResultVO<T> vo = new ResultVO<>();
        vo.setCode(code);
        vo.setMessage(message);
        return vo;
    }
}
