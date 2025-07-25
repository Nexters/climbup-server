package com.climbup.climbup.user.exception;

import com.climbup.climbup.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserOnboardingAlreadyCompleteException extends BusinessException {
    public UserOnboardingAlreadyCompleteException(String message) {
        super("USER_001", message, HttpStatus.CONFLICT);
    }
}
