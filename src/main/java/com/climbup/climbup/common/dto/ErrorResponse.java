package com.climbup.climbup.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "에러 응답 정보")
public class ErrorResponse {

    @Schema(description = "에러 코드", example = "USER_001")
    private String errorCode;

    @Schema(description = "에러 메시지", example = "이미 온보딩을 완료한 사용자입니다.")
    private String message;

    @Schema(description = "에러 발생 시간", example = "2024-01-01T12:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "요청 경로", example = "/api/users/onboarding")
    private String path;

    public static ErrorResponse of(String errorCode, String message, String path) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }
}