package com.climbup.climbup.route.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "루트미션 수정 요청")
public class UpdateRouteMissionRequest {

    @Schema(description = "클라이밍장 ID", example = "1")
    private Long gymId;

    @Schema(description = "섹터 ID", example = "1")
    private Long sectorId;

    @Schema(description = "난이도", example = "RED")
    private String difficulty;

    @Positive(message = "점수는 양수여야 합니다")
    @Schema(description = "점수", example = "150")
    private Integer score;
}