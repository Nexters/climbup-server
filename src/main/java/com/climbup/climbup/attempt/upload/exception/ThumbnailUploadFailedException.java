package com.climbup.climbup.attempt.upload.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class ThumbnailUploadFailedException extends BusinessException {
    public ThumbnailUploadFailedException(Long attemptId, Throwable cause) {
        super(ErrorCode.THUMBNAIL_UPLOAD_FAILED, cause, "도전 ID: " + attemptId);
    }
}