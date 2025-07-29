package com.climbup.climbup.auth.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class TokenExpiredException extends BusinessException {
    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }

    public TokenExpiredException(String message) {
        super(ErrorCode.INVALID_TOKEN, message);
    }

    public TokenExpiredException(Throwable cause) {
        super(ErrorCode.TOKEN_EXPIRED, cause);
    }
}