package com.climbup.climbup.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OnboardingDto {

    @Getter
    @NoArgsConstructor
    @Schema(description = "온보딩 완료 요청")
    public static class CompleteRequest {
        @Schema(description = "암장 ID", example = "1")
        private Long gymId;

        @Schema(description = "레벨 ID", example = "1")
        private Long levelId;
    }

    @Getter
    @NoArgsConstructor
    @Schema(description = "암장 선택 요청")
    public static class GymRequest {
        @Schema(description = "암장 ID", example = "1")
        private Long gymId;
    }

    @Getter
    @NoArgsConstructor
    @Schema(description = "레벨 선택 요청")
    public static class LevelRequest {
        @Schema(description = "레벨 ID", example = "1")
        private Long levelId;
    }

    @Getter
    @Schema(description = "온보딩 응답")
    public static class Response {
        @Schema(description = "결과 메시지", example = "온보딩이 완료되었습니다.")
        private final String message;

        public Response(String message) {
            this.message = message;
        }
    }
}