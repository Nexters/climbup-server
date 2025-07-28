package com.climbup.climbup.session.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class UserSessionNotYetFinishedException extends BusinessException {
    public UserSessionNotYetFinishedException() {
        super(ErrorCode.SESSION_NOT_YET_FINISHED);
    }
}
