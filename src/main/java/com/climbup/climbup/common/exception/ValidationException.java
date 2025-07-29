package com.climbup.climbup.common.exception;

public class ValidationException extends BusinessException {

    public ValidationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ValidationException(ErrorCode errorCode, Object... messageArgs) {
        super(errorCode, messageArgs);
    }
}