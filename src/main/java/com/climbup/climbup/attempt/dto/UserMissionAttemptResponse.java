package com.climbup.climbup.attempt.dto;

import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "사용자 미션 시도 정보 응답")
public class UserMissionAttemptResponse {

    @Schema(description = "미션 시도 ID", example = "1")
    private Long missionAttemptId;

    @Schema(description = "성공 여부", example = "true")
    private Boolean success;

    @Schema(description = "시도 영상 URL", example = "https://example.com/attempt1.mp4")
    private String videoUrl;

    @Schema(description = "시도 생성 시간", example = "2025-07-31T14:20:00")
    private LocalDateTime createdAt;

    public static UserMissionAttemptResponse toDto(UserMissionAttempt attempt) {
        return UserMissionAttemptResponse.builder()
                .missionAttemptId(attempt.getId())
                .success(attempt.getSuccess())
                .videoUrl(attempt.getVideoUrl())
                .createdAt(attempt.getCreatedAt())
                .build();
    }
}