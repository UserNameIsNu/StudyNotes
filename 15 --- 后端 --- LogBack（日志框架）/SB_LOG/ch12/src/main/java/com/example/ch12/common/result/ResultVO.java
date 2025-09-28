package com.example.ch12.common.result;

public class ResultVO {
    private Integer code;
    private String message;
    private Object Data;

    public ResultVO(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        Data = data;
    }

    public static <T> ResultVO success(T data) {
        return new ResultVO(200, null, data);
    }

    public static ResultVO success() {
        return new ResultVO(200, null, null);
    }

    public static ResultVO error(int code, String message) {
        return new ResultVO(code, message, null);
    }
}
