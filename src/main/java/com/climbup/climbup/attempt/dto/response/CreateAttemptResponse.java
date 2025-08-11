package com.climbup.climbup.attempt.dto.response;

import com.climbup.climbup.attempt.enums.AttemptStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "루트미션 도전기록 등록 응답")
public class CreateAttemptResponse {

    @Schema(description = "도전기록 ID", example = "1")
    private Long missionAttemptId;

    @Schema(description = "성공 여부", example = "true")
    private Boolean success;

    @Schema(description = "도전 상태", example = "PENDING_UPLOAD")
    private AttemptStatus status;

    @Schema(description = "생성 시간", example = "2025-07-31T14:20:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(description = "획득한 SR", example = "100")
    private Integer srGained;

    @Schema(description = "현재 총 SR", example = "1250")
    private Integer currentSr;
}