package com.climbup.climbup.auth.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class NicknameGenerationException extends BusinessException {
    public NicknameGenerationException() {
        super(ErrorCode.NICKNAME_GENERATE_ERROR);
    }

    public NicknameGenerationException(String reason) {
        super(ErrorCode.NICKNAME_GENERATE_ERROR, reason);
    }
}