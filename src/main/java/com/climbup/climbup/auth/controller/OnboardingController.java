package com.climbup.climbup.auth.controller;

import com.climbup.climbup.auth.dto.OnboardingDto;
import com.climbup.climbup.auth.service.OnboardingService;
import com.climbup.climbup.auth.util.JwtUtil;
import com.climbup.climbup.user.docs.UserApiDocs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Onboarding", description = "사용자 온보딩 API")
@RestController
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
public class OnboardingController {

    private final OnboardingService onboardingService;
    private final JwtUtil jwtUtil;

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
    @PostMapping("/complete")
    public ResponseEntity<OnboardingDto.Response> completeOnboarding(
            @Parameter(
                    description = UserApiDocs.AUTHORIZATION_DESCRIPTION,
                    required = true,
                    example = UserApiDocs.AUTHORIZATION_EXAMPLE
            )
            @RequestHeader("Authorization") String authorization,
            @RequestBody OnboardingDto.CompleteRequest request) {

        String token = authorization.replace("Bearer ", "");
        Long userId = jwtUtil.getUserId(token);

        onboardingService.completeOnboarding(userId, request.getGymId(), request.getLevelId());

        return ResponseEntity.ok(new OnboardingDto.Response("온보딩이 완료되었습니다."));
    }

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
    public ResponseEntity<OnboardingDto.Response> setGym(
            @Parameter(
                    description = UserApiDocs.AUTHORIZATION_DESCRIPTION,
                    required = true,
                    example = UserApiDocs.AUTHORIZATION_EXAMPLE
            )
            @RequestHeader("Authorization") String authorization,
            @RequestBody OnboardingDto.GymRequest request) {

        String token = authorization.replace("Bearer ", "");
        Long userId = jwtUtil.getUserId(token);

        onboardingService.setUserGym(userId, request.getGymId());

        return ResponseEntity.ok(new OnboardingDto.Response("암장이 설정되었습니다."));
    }

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
    public ResponseEntity<OnboardingDto.Response> setLevel(
            @Parameter(
                    description = UserApiDocs.AUTHORIZATION_DESCRIPTION,
                    required = true,
                    example = UserApiDocs.AUTHORIZATION_EXAMPLE
            )
            @RequestHeader("Authorization") String authorization,
            @RequestBody OnboardingDto.LevelRequest request) {

        String token = authorization.replace("Bearer ", "");
        Long userId = jwtUtil.getUserId(token);

        onboardingService.setUserLevel(userId, request.getLevelId());

        return ResponseEntity.ok(new OnboardingDto.Response("레벨이 설정되었습니다."));
    }
}