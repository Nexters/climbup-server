package com.climbup.climbup.sector.dto;

import com.climbup.climbup.sector.entity.Sector;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "섹터 정보 응답")
public class SectorResponse {

    @Schema(description = "섹터 ID", example = "1")
    private Long id;

    @Schema(description = "섹터 이름", example = "1·2")
    private String name;

    @Schema(description = "섹터 이미지 URL", example = "https://example.com/sector1.jpg")
    private String imageUrl;

    public static SectorResponse toDto(Sector sector) {
        return SectorResponse.builder()
                .id(sector.getId())
                .name(sector.getName())
                .imageUrl(sector.getImageUrl())
                .build();
    }
}