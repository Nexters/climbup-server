package com.climbup.climbup.route.dto.response;

import com.climbup.climbup.route.entity.RouteMission;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "루트미션 응답")
public class RouteMissionResponse {

    @Schema(description = "루트미션 ID", example = "1")
    private Long id;

    @Schema(description = "클라이밍장 ID", example = "1")
    private Long gymId;

    @Schema(description = "클라이밍장 이름", example = "더클라임 강남점")
    private String gymName;

    @Schema(description = "섹터 ID", example = "1")
    private Long sectorId;

    @Schema(description = "섹터 이름", example = "A구역")
    private String sectorName;

    @Schema(description = "난이도", example = "BLUE")
    private String difficulty;

    @Schema(description = "점수", example = "100")
    private Integer score;

    @Schema(description = "루트 이미지 URL", example = "https://example.com/route-image.jpg")
    private String imageUrl;

    @Schema(description = "가이드 영상 썸네일 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnailUrl;

    @Schema(description = "가이드 영상 URL", example = "https://example.com/guide-video.mp4")
    private String videoUrl;

    @Schema(description = "게시 시간", example = "2025-08-10T15:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime postedAt;

    @Schema(description = "생성 시간", example = "2025-08-10T15:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(description = "수정 시간", example = "2025-08-10T15:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public static RouteMissionResponse from(RouteMission mission) {
        return RouteMissionResponse.builder()
                .id(mission.getId())
                .gymId(mission.getGym().getId())
                .gymName(mission.getGym().getName())
                .sectorId(mission.getSector().getId())
                .sectorName(mission.getSector().getName())
                .difficulty(mission.getDifficulty())
                .score(mission.getScore())
                .imageUrl(mission.getImageUrl())
                .thumbnailUrl(mission.getThumbnailUrl())
                .videoUrl(mission.getVideoUrl())
                .postedAt(mission.getPostedAt())
                .createdAt(mission.getCreatedAt())
                .updatedAt(mission.getUpdatedAt())
                .build();
    }
}
