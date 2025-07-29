package com.climbup.climbup.common.exception;

public class CommonBusinessException extends BusinessException {

    public CommonBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CommonBusinessException(ErrorCode errorCode, Object... messageArgs) {
        super(errorCode, messageArgs);
    }

    public CommonBusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public CommonBusinessException(ErrorCode errorCode, Throwable cause, Object... messageArgs) {
        super(errorCode, cause, messageArgs);
    }
}