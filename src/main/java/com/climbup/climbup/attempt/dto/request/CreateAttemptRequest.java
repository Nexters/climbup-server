package com.climbup.climbup.attempt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "루트미션 도전기록 등록 요청")
public class CreateAttemptRequest {

    @NotNull(message = "미션 ID는 필수입니다")
    @Schema(description = "루트미션 ID", example = "1")
    private Long missionId;

    @NotNull(message = "성공 여부는 필수입니다")
    @Schema(description = "도전 성공 여부", example = "true")
    private Boolean success;
}