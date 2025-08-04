package com.climbup.climbup.attempt.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class UploadSessionChunkIncompleteException extends BusinessException {
    public UploadSessionChunkIncompleteException() {
        super(ErrorCode.UPLOAD_SESSION_CHUNK_INCOMPLETE);
    }
}
