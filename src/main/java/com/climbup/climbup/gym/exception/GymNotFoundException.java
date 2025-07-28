package com.climbup.climbup.gym.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class GymNotFoundException extends BusinessException {
    public GymNotFoundException() {
        super(ErrorCode.GYM_NOT_FOUND);
    }

    public GymNotFoundException(Long gymId) {
        super(ErrorCode.GYM_NOT_FOUND, "암장 ID: " + gymId);
    }
}