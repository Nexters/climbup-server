package com.climbup.climbup.attempt.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Schema(description = "세션 도전기록 상세")
public class SessionAttemptDetail {

    @Schema(description = "도전기록 성공 여부")
    private Boolean success;

    @Schema(description = "난이도", example = "BLUE")
    private String difficulty;

    @Schema(description = "난이도 이미지 URL 목록", example = "[\"https://example.com/blue1.png\", \"https://example.com/blue2.png\", \"https://example.com/blue3.png\", \"https://example.com/blue4.png\"]")
    private List<String> difficultyImageUrls;

    @Schema(description = "루트미션 탈거일", example = "2025-08-15T10:00:00")
    private LocalDateTime removedAt;

    @Schema(description = "가이드 썸네일 URL", example = "https://example.com/guide_thumb.jpg")
    private String guideThumbnailUrl;

    @Schema(description = "가이드 비디오 URL", example = "https://example.com/guide_video.mp4")
    private String guideVideoUrl;

    @Schema(description = "섹터 이름", example = "A섹터")
    private String sectorName;

    @Schema(description = "루트미션 점수", example = "150")
    private Integer score;

    @Schema(description = "도전기록 썸네일 URL", example = "https://example.com/attempt_thumb.jpg")
    private String attemptThumbnailUrl;

    @Schema(description = "도전기록 비디오 URL", example = "https://example.com/attempt_video.mp4")
    private String attemptVideoUrl;

    public SessionAttemptDetail(Boolean success, String difficulty, List<String> difficultyImageUrls,
                                LocalDateTime removedAt, String guideThumbnailUrl, String guideVideoUrl,
                                String sectorName, Integer score, String attemptThumbnailUrl, String attemptVideoUrl) {
        this.success = success;
        this.difficulty = difficulty;
        this.difficultyImageUrls = difficultyImageUrls;
        this.removedAt = removedAt;
        this.guideThumbnailUrl = guideThumbnailUrl;
        this.guideVideoUrl = guideVideoUrl;
        this.sectorName = sectorName;
        this.score = score;
        this.attemptThumbnailUrl = attemptThumbnailUrl;
        this.attemptVideoUrl = attemptVideoUrl;
    }
}