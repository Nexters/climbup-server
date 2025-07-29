package com.climbup.climbup.gym.controller;

import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.gym.dto.response.GymLevelResponse;
import com.climbup.climbup.gym.service.GymLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Gym Level", description = "암장 레벨 관련 API")
@RestController
@RequestMapping("/api/gym-levels")
@RequiredArgsConstructor
public class GymLevelController {

    private final GymLevelService gymLevelService;

    @Operation(summary = "특정 암장 레벨 정보 조회", description = "암장레벨 ID로 특정 암장레벨의 상세 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "암장레벨 정보 조회 성공")
    @GetMapping("/{gymLevelId}")
    public ResponseEntity<ApiResult<GymLevelResponse>> getGymLevelById(
            @Parameter(description = "암장레벨 ID") @PathVariable Long gymLevelId) {
        return ResponseEntity.ok(ApiResult.success(gymLevelService.getGymLevelById(gymLevelId)));
    }

}
