package com.climbup.climbup.user.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

    public UserNotFoundException(Long userId) {
        super(ErrorCode.USER_NOT_FOUND, "사용자 ID: " + userId);
    }
}