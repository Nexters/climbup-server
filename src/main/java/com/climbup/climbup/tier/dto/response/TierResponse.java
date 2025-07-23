package com.climbup.climbup.tier.dto.response;

import com.climbup.climbup.tier.entity.Tier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TierResponse {

    @Schema(description = "티어 id")
    private Long id;

    @Schema(description = "티어 이름")
    private String name;

    @Schema(description = "티어 이미지 주소")
    private String imageUrl;

    public static TierResponse toDto(Tier tier) {
        return TierResponse.builder()
                .id(tier.getId())
                .name(tier.getName())
                .imageUrl(tier.getImageUrl())
                .build();
    }
}
