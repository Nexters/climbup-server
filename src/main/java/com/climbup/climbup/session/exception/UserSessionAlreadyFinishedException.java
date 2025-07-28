package com.climbup.climbup.session.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserSessionAlreadyFinishedException extends BusinessException {
    public UserSessionAlreadyFinishedException() {
        super(ErrorCode.SESSION_ALREADY_FINISHED);
    }
}
