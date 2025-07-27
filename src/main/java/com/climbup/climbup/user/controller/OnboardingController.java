package com.climbup.climbup.user.controller;

import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.user.dto.OnboardingDto;
import com.climbup.climbup.auth.util.SecurityUtil;
import com.climbup.climbup.user.service.OnboardingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Onboarding", description = "사용자 온보딩 API")
@RestController
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
public class OnboardingController {

    private final OnboardingService onboardingService;

    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "온보딩 완료",
            description = "암장과 레벨을 동시에 설정하여 온보딩을 완료합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "온보딩 완료 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "이미 온보딩 완료된 사용자",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "errorCode": "USER_001",
                                                "message": "이미 온보딩을 완료한 사용자입니다.",
                                                "timestamp": "2024-01-15T10:30:00",
                                                "path": "/api/onboarding/complete"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ApiResult<OnboardingDto.Response>> completeOnboarding(
            @RequestBody OnboardingDto.CompleteRequest request) {

        Long userId = SecurityUtil.getCurrentUserId();

        onboardingService.completeOnboarding(userId, request.getGymId(), request.getLevelId());

        return ResponseEntity.ok(ApiResult.success(new OnboardingDto.Response("온보딩이 완료되었습니다.")));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "암장 선택",
            description = "사용자의 암장을 설정합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "암장 설정 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "이미 온보딩 완료된 사용자",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "errorCode": "USER_001",
                                                "message": "이미 온보딩을 완료한 사용자입니다.",
                                                "timestamp": "2024-01-15T10:30:00",
                                                "path": "/api/onboarding/gym"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/gym")
    public ResponseEntity<ApiResult<OnboardingDto.Response>> setGym(
            @RequestBody OnboardingDto.GymRequest request) {

        Long userId = SecurityUtil.getCurrentUserId();

        onboardingService.setUserGym(userId, request.getGymId());

        return ResponseEntity.ok(ApiResult.success(new OnboardingDto.Response("암장이 설정되었습니다.")));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "레벨 선택",
            description = "사용자의 레벨을 설정합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "레벨 설정 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "이미 온보딩 완료된 사용자",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "errorCode": "USER_001",
                                                "message": "이미 온보딩을 완료한 사용자입니다.",
                                                "timestamp": "2024-01-15T10:30:00",
                                                "path": "/api/onboarding/level"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/level")
    public ResponseEntity<ApiResult<OnboardingDto.Response>> setLevel(
            @RequestBody OnboardingDto.LevelRequest request) {

        Long userId = SecurityUtil.getCurrentUserId();

        onboardingService.setUserLevel(userId, request.getLevelId());

        return ResponseEntity.ok(ApiResult.success(new OnboardingDto.Response("레벨이 설정되었습니다.")));
    }
}