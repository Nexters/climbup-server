package com.climbup.climbup.gym.controller;

import com.climbup.climbup.auth.util.SecurityUtil;
import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.gym.dto.response.GymAttemptResponse;
import com.climbup.climbup.gym.dto.response.GymResponse;
import com.climbup.climbup.gym.service.GymService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Climbing Gym", description = "암장 관련 API")
@RestController
@RequestMapping("/api/gyms")
@RequiredArgsConstructor
public class GymController {

    private final GymService gymService;

    @Operation(summary = "전체 암장 리스트 조회", description = "모든 브랜드의 모든 암장을 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공적으로 모든 암장 리스트를 반환")
    @GetMapping
    public ResponseEntity<ApiResult<List<GymResponse>>> getAllGyms() {
        return ResponseEntity.ok(ApiResult.success(gymService.getAllGyms()));
    }

    @Operation(summary = "특정 암장 정보 조회", description = "암장 ID로 특정 암장의 상세 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "암장 정보 조회 성공")
    @GetMapping("/{gymId}")
    public ResponseEntity<ApiResult<GymResponse>> getGymById(
            @Parameter(description = "암장 ID") @PathVariable Long gymId) {
        return ResponseEntity.ok(ApiResult.success(gymService.getGymById(gymId)));
    }

    @Operation(summary = "브랜드별 암장 리스트 조회", description = "특정 브랜드의 모든 지점을 조회합니다")
    @ApiResponse(responseCode = "200", description = "브랜드별 암장 리스트 조회 성공")
    @GetMapping("/brands/{brandId}")
    public ResponseEntity<ApiResult<List<GymResponse>>> getGymsByBrand(
            @Parameter(description = "브랜드 ID") @PathVariable Long brandId) {
        return ResponseEntity.ok(ApiResult.success(gymService.getGymsByBrandId(brandId)));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "내 암장별 성공한 도전 기록 조회",
            description = "사용자가 특정 암장에서 성공한 도전 기록들을 최신순으로 조회합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/{gymId}/attempts/my-success")
    public ResponseEntity<ApiResult<Page<GymAttemptResponse>>> getMySuccessfulAttempts(
            @Parameter(description = "암장 ID", example = "1")
            @PathVariable Long gymId,

            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        Long userId = SecurityUtil.getCurrentUserId();

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<GymAttemptResponse> response = gymService.getSuccessfulAttemptsByGym(gymId, userId, pageable);

        return ResponseEntity.ok(
                ApiResult.success("성공한 도전 기록이 조회되었습니다.", response)
        );
    }
}