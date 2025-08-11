package com.climbup.climbup.attempt.upload.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class VideoUploadFailedException extends BusinessException {
    public VideoUploadFailedException(Throwable cause) {
        super(ErrorCode.VIDEO_UPLOAD_FAILED, cause);
    }
}