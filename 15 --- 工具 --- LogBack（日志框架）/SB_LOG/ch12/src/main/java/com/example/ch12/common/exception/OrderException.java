package com.example.ch12.common.exception;

public class OrderException extends GlobalException{
    public OrderException(String message, Integer errorCode) {
        super(message, errorCode);
    }
}
