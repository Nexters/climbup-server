package com.climbup.climbup.gym.dto.response;

import com.climbup.climbup.gym.entity.ClimbingGym;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "암장 정보")
public class GymResponse {

    @Schema(description = "암장 ID", example = "1")
    private Long id;

    @Schema(description = "암장 이름", example = "더클라임")
    private String name;

    @Schema(description = "지점명", example = "강남점")
    private String location;

    @Schema(description = "주소", example = "서울특별시 강남구 테헤란로8길 21 지하 1층")
    private String address;

    @Schema(description = "암장 이미지 URL", example = "https://example.com/gym1.jpg")
    private String imageUrl;

    public static GymResponse fromEntity(ClimbingGym gym) {
        return GymResponse.builder()
                .id(gym.getId())
                .name(gym.getName())
                .location(gym.getLocation())
                .address(gym.getAddress())
                .imageUrl(gym.getImageUrl())
                .build();
    }
}