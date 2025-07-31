package com.climbup.climbup.attempt.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class AttemptNotFoundException extends BusinessException {
    public AttemptNotFoundException() {
        super(ErrorCode.ATTEMPT_NOT_FOUND);
    }
}