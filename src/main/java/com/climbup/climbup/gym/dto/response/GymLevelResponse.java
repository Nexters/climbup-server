package com.climbup.climbup.gym.dto.response;

import com.climbup.climbup.gym.entity.GymLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "암장별 레벨 정보 응답")
public class GymLevelResponse {

    @Schema(description = "암장별 레벨 ID", example = "1")
    private Long id;

    @Schema(description = "브랜드 ID", example = "1")
    private Long brandId;

    @Schema(description = "브랜드명", example = "더클라임")
    private String brandName;

    @Schema(description = "암장별 레벨명", example = "ORANGE")
    private String gymLevelName;

    @Schema(description = "기본 레벨 ID", example = "1")
    private Long levelId;

    @Schema(description = "기본 레벨명", example = "입문자 단계")
    private String levelName;

    @Schema(description = "레벨 설명", example = "클라이밍이 처음이라면 가볍게 시작해보세요!")
    private String description;

    @Schema(description = "최소 SR", example = "600")
    private Integer srMin;

    @Schema(description = "최대 SR", example = "649")
    private Integer srMax;

    @Schema(description = "암장별 레벨 이미지 URL", example = "https://example.com/orange.png")
    private String imageUrl;

    @Schema(description = "정렬 순서", example = "1")
    private Integer sortOrder;

    public static GymLevelResponse fromEntity(GymLevel gymLevel) {
        return GymLevelResponse.builder()
                .id(gymLevel.getId())
                .brandId(gymLevel.getBrand().getId())
                .brandName(gymLevel.getBrand().getName())
                .gymLevelName(gymLevel.getName())
                .levelId(gymLevel.getLevel().getId())
                .levelName(gymLevel.getLevel().getName())
                .description(gymLevel.getLevel().getDescription())
                .srMin(gymLevel.getSrMin())
                .srMax(gymLevel.getSrMax())
                .imageUrl(gymLevel.getImageUrl())
                .sortOrder(gymLevel.getSortOrder())
                .build();
    }
}