package com.climbup.climbup.brand.dto.response;

import com.climbup.climbup.brand.entity.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "브랜드 정보 응답")
public class BrandResponse {

    @Schema(description = "브랜드 ID", example = "1")
    private Long id;

    @Schema(description = "브랜드명", example = "더클라임")
    private String name;

    @Schema(description = "브랜드 설명", example = "국내 최대 클라이밍 체인")
    private String description;

    @Schema(description = "로고 URL")
    private String logoUrl;

    @Schema(description = "지점 수", example = "15")
    private Integer gymCount;

    public static BrandResponse fromEntity(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .logoUrl(brand.getLogoUrl())
                .gymCount(brand.getGyms().size())
                .build();
    }
}