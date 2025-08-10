package com.climbup.climbup.route.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "루트미션 생성 요청")
public class CreateRouteMissionRequest {

    @NotNull(message = "클라이밍장 ID는 필수입니다")
    @Schema(description = "클라이밍장 ID", example = "1")
    private Long gymId;

    @NotNull(message = "섹터 ID는 필수입니다")
    @Schema(description = "섹터 ID", example = "1")
    private Long sectorId;

    @NotBlank(message = "난이도는 필수입니다")
    @Schema(description = "난이도", example = "BLUE")
    private String difficulty;

    @NotNull(message = "점수는 필수입니다")
    @Positive(message = "점수는 양수여야 합니다")
    @Schema(description = "점수", example = "100")
    private Integer score;
}