package com.climbup.climbup.attempt.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class UploadSessionNotfoundException extends BusinessException {
    public UploadSessionNotfoundException() {
        super(ErrorCode.UPLOAD_SESSION_NOT_FOUND);
    }
}
