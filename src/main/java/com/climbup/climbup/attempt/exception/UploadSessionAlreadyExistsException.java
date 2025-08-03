package com.climbup.climbup.attempt.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class UploadSessionAlreadyExistsException extends BusinessException {
    public UploadSessionAlreadyExistsException() {
        super(ErrorCode.UPLOAD_SESSION_ALREADY_EXISTS);
    }
}
