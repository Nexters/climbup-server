package com.climbup.climbup.user.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class UserOnboardingAlreadyCompleteException extends BusinessException {
    public UserOnboardingAlreadyCompleteException() {
        super(ErrorCode.USER_ONBOARDING_ALREADY_COMPLETE);
    }
}