package com.climbup.climbup.auth.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class AuthHeaderMissingException extends BusinessException {
    public AuthHeaderMissingException() {
        super(ErrorCode.AUTH_HEADER_MISSING, "Authorization");
    }

    public AuthHeaderMissingException(String headerName) {
        super(ErrorCode.AUTH_HEADER_MISSING, headerName);
    }
}