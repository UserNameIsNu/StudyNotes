package com.example.ch12.common.exception;

public class GlobalException extends RuntimeException{
    private Integer errorCode;

    public GlobalException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
