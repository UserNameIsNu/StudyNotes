package edu.nf.like.common.exception;

/**
 * @author wangl
 * @date 2023/12/6
 */
public class LoginException extends RuntimeException {

    private final Integer errorCode;

    public LoginException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}