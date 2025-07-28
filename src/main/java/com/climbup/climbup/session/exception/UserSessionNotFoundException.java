package com.climbup.climbup.session.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserSessionNotFoundException extends BusinessException {
    public UserSessionNotFoundException() {
        super(ErrorCode.SESSION_NOT_FOUND);
    }
}
