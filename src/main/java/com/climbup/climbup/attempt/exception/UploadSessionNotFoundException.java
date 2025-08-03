package com.climbup.climbup.attempt.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class UploadSessionNotFoundException extends BusinessException {
    public UploadSessionNotFoundException() {
        super(ErrorCode.UPLOAD_SESSION_NOT_FOUND);
    }
}
