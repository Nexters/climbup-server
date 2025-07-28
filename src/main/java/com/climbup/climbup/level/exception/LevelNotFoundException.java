package com.climbup.climbup.level.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class LevelNotFoundException extends BusinessException {
    public LevelNotFoundException() {
        super(ErrorCode.LEVEL_NOT_FOUND);
    }

    public LevelNotFoundException(String message) {
        super(ErrorCode.LEVEL_NOT_FOUND, message);
    }

    public LevelNotFoundException(Long levelId) {
        super(ErrorCode.LEVEL_NOT_FOUND, "레벨 ID: " + levelId);
    }
}