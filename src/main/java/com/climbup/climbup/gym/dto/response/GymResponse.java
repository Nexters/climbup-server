package com.climbup.climbup.gym.dto.response;

import com.climbup.climbup.gym.entity.ClimbingGym;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "암장 정보 응답")
public class GymResponse {

    @Schema(description = "암장 ID", example = "1")
    private Long id;

    @Schema(description = "브랜드 ID", example = "1")
    private Long brandId;

    @Schema(description = "브랜드명", example = "더클라임")
    private String brandName;

    @Schema(description = "지점명", example = "강남점")
    private String branchName;

    @Schema(description = "전체 이름", example = "더클라임 강남점")
    private String fullName;

    @Schema(description = "주소", example = "서울특별시 강남구 테헤란로8길 21 지하 1층")
    private String address;

    @Schema(description = "섹터 정보", example = "A섹터: 볼더링, B섹터: 리드클라이밍")
    private String sectorInfo;

    @Schema(description = "이미지 URL")
    private String imageUrl;

    public static GymResponse fromEntity(ClimbingGym gym) {
        return GymResponse.builder()
                .id(gym.getId())
                .brandId(gym.getBrand().getId())
                .brandName(gym.getBrand().getName())
                .branchName(gym.getBranchName())
                .fullName(gym.getName())
                .address(gym.getAddress())
                .sectorInfo(gym.getSectorInfo())
                .imageUrl(gym.getImageUrl())
                .build();
    }
}