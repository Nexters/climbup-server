package com.climbup.climbup.gym.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class GymLevelNotFoundException extends BusinessException {
    public GymLevelNotFoundException() {
        super(ErrorCode.GYM_LEVEL_NOT_FOUND);
    }

    public GymLevelNotFoundException(String message) {
        super(ErrorCode.GYM_LEVEL_NOT_FOUND, message);
    }

    public GymLevelNotFoundException(Long gymLevelId) {
        super(ErrorCode.GYM_LEVEL_NOT_FOUND, "암장 레벨 ID: " + gymLevelId);
    }
}