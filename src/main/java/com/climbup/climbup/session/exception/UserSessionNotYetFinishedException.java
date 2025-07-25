package com.climbup.climbup.session.exception;

import com.climbup.climbup.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserSessionNotYetFinishedException extends BusinessException {
    public UserSessionNotYetFinishedException(String message) {
        super("SESSION_003", message, HttpStatus.CONFLICT);
    }
}
