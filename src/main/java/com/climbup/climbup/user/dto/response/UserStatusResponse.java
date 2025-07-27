package com.climbup.climbup.user.dto.response;

import com.climbup.climbup.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "사용자 상태 정보")
public class UserStatusResponse {

    @Schema(description = "사용자 ID", example = "1")
    private Long id;

    @Schema(description = "이름", example = "이예림")
    private String name;

    @Schema(description = "닉네임", example = "당당한카피바라")
    private String nickname;

    @Schema(description = "프로필 이미지 URL", example = "https://k.kakaocdn.net/dn/profile.jpg")
    private String imageUrl;

    @Schema(description = "SR 점수", example = "600")
    private Integer sr;

    @Schema(description = "온보딩 완료 여부", example = "false")
    private boolean onboardingCompleted;

    @Schema(description = "선택된 레벨 정보")
    private LevelInfo level;

    @Schema(description = "선택된 암장 정보")
    private GymInfo gym;

    @Data
    @Builder
    @Schema(description = "레벨 정보")
    public static class LevelInfo {

        @Schema(description = "레벨 ID", example = "2")
        private Long id;

        @Schema(description = "레벨 이름", example = "GREEN")
        private String name;

        @Schema(description = "레벨 이미지 URL", example = "https://example.com/green.png")
        private String imageUrl;

        @Schema(description = "최소 SR", example = "650")
        private Integer srMin;

        @Schema(description = "최대 SR", example = "999")
        private Integer srMax;
    }

    @Data
    @Builder
    @Schema(description = "암장 정보")
    public static class GymInfo {

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
    }

    public static UserStatusResponse fromEntity(User user) {
        boolean hasGym = user.getGym() != null;
        boolean hasLevel = user.getLevel() != null;

        return UserStatusResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .sr(user.getSr())
                .onboardingCompleted(hasGym && hasLevel)
                .level(hasLevel ? LevelInfo.builder()
                        .id(user.getLevel().getId())
                        .name(user.getLevel().getName())
                        .imageUrl(user.getLevel().getImageUrl())
                        .srMin(user.getLevel().getSrMin())
                        .srMax(user.getLevel().getSrMax())
                        .build() : null)
                .gym(hasGym ? GymInfo.builder()
                        .id(user.getGym().getId())
                        .name(user.getGym().getName())
                        .location(user.getGym().getLocation())
                        .address(user.getGym().getAddress())
                        .imageUrl(user.getGym().getImageUrl())
                        .build() : null)
                .build();
    }
}