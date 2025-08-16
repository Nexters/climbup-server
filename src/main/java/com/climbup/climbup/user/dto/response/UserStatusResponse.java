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

    @Schema(description = "도전 성공 횟수", example = "15")
    private Long successCount;

    @Schema(description = "도전 실패 횟수", example = "5")
    private Long failureCount;

    @Schema(description = "총 도전 횟수", example = "20")
    private Long totalAttempts;

    @Schema(description = "선택된 레벨 정보")
    private GymLevelInfo gymLevel;

    @Schema(description = "선택된 암장 정보")
    private GymInfo gym;

    @Data
    @Builder
    @Schema(description = "암장별 레벨 정보")
    public static class GymLevelInfo {

        @Schema(description = "암장별 레벨 ID", example = "3")
        private Long id;

        @Schema(description = "브랜드명", example = "더클라임")
        private String brandName;

        @Schema(description = "기본 레벨명", example = "V0")
        private String levelName;

        @Schema(description = "암장별 레벨명", example = "ORANGE")
        private String gymLevelName;

        @Schema(description = "최소 SR", example = "600")
        private Integer srMin;

        @Schema(description = "최대 SR", example = "649")
        private Integer srMax;

        @Schema(description = "정렬 순서", example = "1")
        private Integer sortOrder;
    }

    @Data
    @Builder
    @Schema(description = "암장 정보")
    public static class GymInfo {

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

        @Schema(description = "암장 이미지 URL", example = "https://example.com/gym1.jpg")
        private String imageUrl;
    }

    public static UserStatusResponse fromEntity(User user) {
        boolean hasGym = user.getGym() != null;
        boolean hasGymLevel = user.getGymLevel() != null;

        // 도전 기록 통계 계산
        long successCount = user.getAttempts().stream()
                .filter(attempt -> attempt.getSuccess() != null && attempt.getSuccess())
                .count();

        long failureCount = user.getAttempts().stream()
                .filter(attempt -> attempt.getSuccess() != null && !attempt.getSuccess())
                .count();

        long totalAttempts = successCount + failureCount;

        return UserStatusResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .sr(user.getSr())
                .onboardingCompleted(user.hasCompletedOnboarding())
                .successCount(successCount)
                .failureCount(failureCount)
                .totalAttempts(totalAttempts)
                .gymLevel(hasGymLevel ? GymLevelInfo.builder()
                        .id(user.getGymLevel().getId())
                        .brandName(user.getGymLevel().getBrand().getName())
                        .levelName(user.getGymLevel().getLevel().getName())
                        .gymLevelName(user.getGymLevel().getName())
                        .srMin(user.getGymLevel().getSrMin())
                        .srMax(user.getGymLevel().getSrMax())
                        .sortOrder(user.getGymLevel().getSortOrder())
                        .build() : null)
                .gym(hasGym ? GymInfo.builder()
                        .id(user.getGym().getId())
                        .brandId(user.getGym().getBrand().getId())
                        .brandName(user.getGym().getBrand().getName())
                        .branchName(user.getGym().getBranchName())
                        .fullName(user.getGym().getName())
                        .address(user.getGym().getAddress())
                        .imageUrl(user.getGym().getImageUrl())
                        .build() : null)
                .build();
    }
}