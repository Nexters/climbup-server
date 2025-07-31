package com.climbup.climbup.recommendation.controller;

import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.recommendation.dto.request.RouteMissionRecommendationResponse;
import com.climbup.climbup.recommendation.entity.ChallengeRecommendation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@Tag(name = "Route Mission Recommendations", description = "루트 미션 추천 관련 API")
@RequiredArgsConstructor
public class RecommendationController {

    @Operation(summary = "루트미션 리스트 불러오기", description = "유저의 난이도에 맞는 추천 루트미션 리스트", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", 
                    description = "유저 추천 루트미션 리스트 반환",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RouteMissionRecommendationResponse.class),
                            examples = @ExampleObject(
                                    name = "성공적인 추천 루트미션 리스트 응답",
                                    value = """
                                    {
                                        "message": "추천 루트미션 리스트를 성공적으로 조회했습니다.",
                                        "data": [
                                            {
                                                "missionId": 1,
                                                "gymId": 1,
                                                "attempts": [
                                                    {
                                                        "missionAttemptId": 1,
                                                        "success": true,
                                                        "videoUrl": "https://example.com/attempt1.mp4",
                                                        "createdAt": "2025-07-31T14:20:00"
                                                    }
                                                ],
                                                "sector": {
                                                    "id": 1,
                                                    "name": "A 섹터",
                                                    "imageUrl": "https://example.com/sector1.jpg"
                                                },
                                                "difficulty": "V3",
                                                "score": 100,
                                                "imageUrl": "https://example.com/mission1.jpg",
                                                "videoUrl": "https://example.com/mission1.mp4",
                                                "removedAt": "2025-07-31T14:20:00",
                                                "postedAt": "2025-07-31T14:20:00",
                                                "recommendedOrder": 1
                                            },
                                            {
                                                "missionId": 2,
                                                "gymId": 1,
                                                "attempts": [],
                                                "sector": {
                                                    "id": 2,
                                                    "name": "B 섹터",
                                                    "imageUrl": "https://example.com/sector2.jpg"
                                                },
                                                "difficulty": "V4",
                                                "score": 150,
                                                "imageUrl": "https://example.com/mission2.jpg",
                                                "videoUrl": "https://example.com/mission2.mp4",
                                                "removedAt": "2025-07-31T14:20:00",
                                                "postedAt": "2025-07-31T14:20:00",
                                                "recommendedOrder": 2
                                            }
                                        ]
                                    }
                                    """
                            )
                    )
            )
    })
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResult<List<RouteMissionRecommendationResponse>>> getRouteMissionRecommendations() {
        return ResponseEntity.ok(ApiResult.success(List.of(RouteMissionRecommendationResponse.builder().build())));
    }
}
