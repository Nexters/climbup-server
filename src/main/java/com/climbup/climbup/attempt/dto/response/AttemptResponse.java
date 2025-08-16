package com.climbup.climbup.attempt.dto.response;

import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Schema(description = "암장 도전 기록 응답")
public class AttemptResponse {

    @Schema(description = "도전 기록 ID", example = "1")
    private Long attemptId;

    @Schema(description = "암장 ID", example = "1")
    private Long gymId;

    @Schema(description = "브랜드명", example = "더클라임")
    private String brandName;

    @Schema(description = "지점명", example = "강남점")
    private String branchName;

    @Schema(description = "암장별 레벨명", example = "ORANGE")
    private String gymLevelName;

    @Schema(description = "암장별 레벨 이미지 URL 목록")
    private List<String> gymLevelImageUrls;

    @Schema(description = "도전 일자", example = "2024-08-16T10:30:00")
    private LocalDateTime attemptedAt;

    @Schema(description = "성공 여부", example = "true")
    private Boolean success;

    @Schema(description = "썸네일 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnailUrl;

    @Schema(description = "비디오 URL", example = "https://example.com/video.mp4")
    private String videoUrl;

    @Schema(description = "섹터명", example = "1·2")
    private String sectorName;

    @Schema(description = "루트 점수", example = "100")
    private Integer routeScore;

    public static AttemptResponse fromEntity(UserMissionAttempt attempt) {
        return AttemptResponse.builder()
                .attemptId(attempt.getId())
                .gymId(attempt.getMission().getGym().getId())
                .brandName(attempt.getMission().getGym().getBrand().getName())
                .branchName(attempt.getMission().getGym().getBranchName())
                .gymLevelName(attempt.getUser().getGymLevel().getName())
                .gymLevelImageUrls(attempt.getUser().getGymLevel().getImageUrls())
                .attemptedAt(attempt.getCreatedAt())
                .success(attempt.getSuccess())
                .thumbnailUrl(attempt.getThumbnailUrl())
                .videoUrl(attempt.getVideoUrl())
                .sectorName(attempt.getMission().getSector().getName())
                .routeScore(attempt.getMission().getScore())
                .build();
    }
}