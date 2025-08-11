package com.climbup.climbup.attempt.upload.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class ImageSaveFailedException extends BusinessException {
    public ImageSaveFailedException(String fileName, Throwable cause) {
        super(ErrorCode.IMAGE_SAVE_FAILED, cause, "파일명: " + fileName);
    }
}