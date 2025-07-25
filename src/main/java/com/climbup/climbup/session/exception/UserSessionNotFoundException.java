package com.climbup.climbup.session.exception;

import com.climbup.climbup.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserSessionNotFoundException extends BusinessException {
    public UserSessionNotFoundException(String message) {
        super("SESSION_001", message, HttpStatus.NOT_FOUND);
    }
}
