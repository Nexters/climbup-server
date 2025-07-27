package com.climbup.climbup.auth.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }

    public InvalidTokenException(String tokenType) {
        super(ErrorCode.INVALID_TOKEN, tokenType);
    }

    public InvalidTokenException(String tokenType, Throwable cause) {
        super(ErrorCode.INVALID_TOKEN, cause, tokenType);
    }
}