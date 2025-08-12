package com.climbup.climbup.recommendation.controller;

import com.climbup.climbup.auth.util.SecurityUtil;
import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.recommendation.dto.response.RouteMissionRecommendationResponse;
import com.climbup.climbup.recommendation.service.RecommendationService;
import com.climbup.climbup.session.repository.UserSessionRepository;
import com.climbup.climbup.session.service.UserSessionService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@Tag(name = "Route Mission Recommendations", description = "루트 미션 추천 관련 API")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Operation(summary = "루트미션 리스트 불러오기", description = "유저의 난이도에 맞는 추천 루트미션 리스트", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "유저 추천 루트미션 리스트 반환")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResult<List<RouteMissionRecommendationResponse>>> getRouteMissionRecommendations() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<RouteMissionRecommendationResponse> routeMissionRecommendationResponses = recommendationService.getRecommendationsByUserActiveSession(userId);
        return ResponseEntity.ok(ApiResult.success(routeMissionRecommendationResponses));
    }
}
