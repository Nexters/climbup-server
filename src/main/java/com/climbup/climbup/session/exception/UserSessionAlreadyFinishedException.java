package com.climbup.climbup.session.exception;

import com.climbup.climbup.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserSessionAlreadyFinishedException extends BusinessException {
    public UserSessionAlreadyFinishedException(String message) {
        super("SESSION_002", message, HttpStatus.CONFLICT);
    }
}
