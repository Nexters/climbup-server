package com.climbup.climbup.recommendation.dto.response;

import com.climbup.climbup.attempt.dto.response.UserMissionAttemptResponse;
import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.recommendation.entity.ChallengeRecommendation;
import com.climbup.climbup.route.entity.RouteMission;
import com.climbup.climbup.sector.dto.SectorResponse;
import com.climbup.climbup.sector.entity.Sector;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Schema(description = "추천 루트미션 리스트 응답")
public class RouteMissionRecommendationResponse {

    @Schema(description = "루트미션 ID", example = "1")
    private Long missionId;

    @Schema(description = "암장 ID", example = "1")
    private Long gymId;

    @Schema(description = "루트 미션 시도 리스트")
    private List<UserMissionAttemptResponse> attempts;

    @Schema(description = "섹터 데이터")
    private SectorResponse sector;

    @Schema(description = "루트미션 난이도")
    private String difficulty;

    @Schema(description = "루트 미션 상승 점수")
    private Integer score;

    @Schema(description = "루트미션 설명 이미지 주소")
    private String imageUrl;

    @Schema(description = "루트미션 해설 영상 주소")
    private String videoUrl;

    @Schema(description = "루트미션 탈거 날짜")
    private LocalDateTime removedAt;

    @Schema(description = "루트미션 추가 날짜")
    private LocalDateTime postedAt;

    @Schema(description = "루트미션 추천 순서")
    private Integer recommendedOrder;

    public static RouteMissionRecommendationResponse toDto(ChallengeRecommendation recommendation, RouteMission mission, ClimbingGym gym, List<UserMissionAttempt> attempts, Sector sector, Integer recommendedOrder){
        return RouteMissionRecommendationResponse.builder()
                .missionId(mission.getId())
                .gymId(gym.getId())
                .attempts(attempts.stream().map(UserMissionAttemptResponse::toDto).toList())
                .sector(SectorResponse.toDto(sector))
                .difficulty(recommendation.getDifficulty())
                .score(mission.getScore())
                .imageUrl(mission.getImageUrl())
                .videoUrl(mission.getVideoUrl())
                .removedAt(mission.getRemovedAt())
                .postedAt(mission.getPostedAt())
                .recommendedOrder(recommendedOrder)
                .build();
    }
}
