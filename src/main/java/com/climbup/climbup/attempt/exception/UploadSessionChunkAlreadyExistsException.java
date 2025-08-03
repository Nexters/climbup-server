package com.climbup.climbup.attempt.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class UploadSessionChunkAlreadyExistsException extends BusinessException {
  public UploadSessionChunkAlreadyExistsException() {
    super(ErrorCode.UPLOAD_SESSION_CHUNK_ALREADY_EXISTS);
  }
}
