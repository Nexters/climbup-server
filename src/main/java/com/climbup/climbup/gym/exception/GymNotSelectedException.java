package com.climbup.climbup.gym.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class GymNotSelectedException extends BusinessException {
    public GymNotSelectedException() {
        super(ErrorCode.GYM_NOT_SELECTED);
    }
}