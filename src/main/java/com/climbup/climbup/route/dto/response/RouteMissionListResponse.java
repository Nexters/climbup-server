package com.climbup.climbup.route.dto.response;

import com.climbup.climbup.route.entity.RouteMission;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "루트미션 목록 응답")
public class RouteMissionListResponse {

    @Schema(description = "루트미션 ID", example = "1")
    private Long id;

    @Schema(description = "클라이밍장 이름", example = "더클라임 강남점")
    private String gymName;

    @Schema(description = "섹터 이름", example = "A구역")
    private String sectorName;

    @Schema(description = "난이도", example = "BLUE")
    private String difficulty;

    @Schema(description = "점수", example = "100")
    private Integer score;

    @Schema(description = "루트 이미지 URL", example = "https://example.com/route-image.jpg")
    private String imageUrl;

    @Schema(description = "썸네일 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnailUrl;

    @Schema(description = "게시 시간", example = "2025-08-10T15:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime postedAt;

    public static RouteMissionListResponse from(RouteMission mission) {
        return RouteMissionListResponse.builder()
                .id(mission.getId())
                .gymName(mission.getGym().getName())
                .sectorName(mission.getSector().getName())
                .difficulty(mission.getDifficulty())
                .score(mission.getScore())
                .imageUrl(mission.getImageUrl())
                .thumbnailUrl(mission.getThumbnailUrl())
                .postedAt(mission.getPostedAt())
                .build();
    }
}