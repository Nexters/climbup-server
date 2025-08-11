package com.climbup.climbup.attempt.dto.response;

import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import com.climbup.climbup.attempt.enums.AttemptStatus;
import com.climbup.climbup.attempt.upload.entity.UploadSession;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "도전 기록 상태 응답")
public class AttemptStatusResponse {

    @Schema(description = "도전기록 ID", example = "1")
    private Long attemptId;

    @Schema(description = "도전 상태", example = "COMPLETED")
    private AttemptStatus status;

    @Schema(description = "업로드 진행률 (0-100)", example = "75")
    private Integer uploadProgress;

    @Schema(description = "상태 메시지", example = "업로드가 완료되었습니다.")
    private String statusMessage;

    public static AttemptStatusResponse from(UserMissionAttempt attempt) {
        return AttemptStatusResponse.builder()
                .attemptId(attempt.getId())
                .status(attempt.getStatus())
                .uploadProgress(calculateUploadProgress(attempt))
                .statusMessage(getStatusMessage(attempt.getStatus()))
                .build();
    }

    private static Integer calculateUploadProgress(UserMissionAttempt attempt) {
        if (attempt.getUpload() == null) return 0;

        UploadSession upload = attempt.getUpload();
        if (upload.getChunkLength() == 0) return 0;

        return (int) ((upload.getReceivedChunkCount() * 100) / upload.getChunkLength());
    }

    private static String getStatusMessage(AttemptStatus status) {
        return switch (status) {
            case PENDING_UPLOAD -> "영상 업로드를 기다리고 있습니다.";
            case UPLOADING -> "영상을 업로드 중입니다.";
            case COMPLETED -> "업로드가 완료되었습니다.";
            case UPLOAD_FAILED -> "업로드에 실패했습니다. 다시 시도해주세요.";
        };
    }
}