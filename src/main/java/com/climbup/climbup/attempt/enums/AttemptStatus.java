package com.climbup.climbup.attempt.enums;

public enum AttemptStatus {
    PENDING_UPLOAD,    // 영상 업로드 대기중
    UPLOADING,         // 업로드 진행중
    COMPLETED,         // 완료
    UPLOAD_FAILED      // 업로드 실패
}