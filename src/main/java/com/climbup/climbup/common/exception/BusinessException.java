package com.climbup.climbup.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object[] messageArgs;

    protected BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.messageArgs = null;
    }

    protected BusinessException(ErrorCode errorCode, Object... messageArgs) {
        super(errorCode.getMessage(messageArgs));
        this.errorCode = errorCode;
        this.messageArgs = messageArgs;
    }

    protected BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.messageArgs = null;
    }

    protected BusinessException(ErrorCode errorCode, Throwable cause, Object... messageArgs) {
        super(errorCode.getMessage(messageArgs), cause);
        this.errorCode = errorCode;
        this.messageArgs = messageArgs;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    public String getFormattedMessage() {
        return errorCode.getMessage(messageArgs);
    }
}