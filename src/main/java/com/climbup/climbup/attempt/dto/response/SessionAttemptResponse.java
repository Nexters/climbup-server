package com.climbup.climbup.attempt.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "세션 도전기록 응답")
public class SessionAttemptResponse {

    @Schema(description = "성공한 도전기록 목록")
    private List<SessionAttemptDetail> successfulAttempts;

    @Schema(description = "실패한 도전기록 목록")
    private List<SessionAttemptDetail> failedAttempts;
}