package com.climbup.climbup.gym.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class GymLevelBrandMismatchException extends BusinessException {
    public GymLevelBrandMismatchException() {
        super(ErrorCode.GYM_LEVEL_BRAND_MISMATCH);
    }
}