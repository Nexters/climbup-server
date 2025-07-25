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

    @Schema(description = "암장 이름", example = "더클라임 강남점")
    private String name;

    @Schema(description = "암장 위치", example = "서울시 강남구")
    private String location;

    @Schema(description = "암장 이미지 URL", example = "https://example.com/gym1.jpg")
    private String imageUrl;

    public static GymResponse fromEntity(ClimbingGym gym) {
        return GymResponse.builder()
                .id(gym.getId())
                .name(gym.getName())
                .location(gym.getLocation())
                .imageUrl(gym.getImageUrl())
                .build();
    }
}