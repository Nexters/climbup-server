package com.climbup.climbup.attempt.controller;


import com.climbup.climbup.attempt.dto.request.CreateAttemptRequest;
import com.climbup.climbup.attempt.dto.response.CreateAttemptResponse;
import com.climbup.climbup.attempt.repository.UserMissionAttemptRepository;
import com.climbup.climbup.attempt.service.AttemptService;
import com.climbup.climbup.attempt.upload.dto.request.RouteMissionUploadChunkRequest;
import com.climbup.climbup.attempt.upload.dto.request.RouteMissionUploadSessionInitializeRequest;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadChunkResponse;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadSessionFinalizeResponse;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadSessionInitializeResponse;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadStatusResponse;
import com.climbup.climbup.auth.util.SecurityUtil;
import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.recommendation.dto.response.RouteMissionRecommendationResponse;
import com.climbup.climbup.recommendation.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attempts")
@Tag(name = "Attempts", description = "루트 미션 도전 관련 API")
@RequiredArgsConstructor
public class AttemptController {

    private final AttemptService attemptService;
    private final RecommendationService recommendationService;
    private final UserMissionAttemptRepository userMissionAttemptRepository;


    @Operation(summary = "도전한 루트미션과 비슷한 난이도의 루트미션 리스트 불러오기", description = "도전한 루트미션과 비슷한 난이도의 루트미션 리스트를 받아보기", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "유저 추천 루트미션 리스트 반환")
    @GetMapping("/{attemptId}/recommendations")
    public ResponseEntity<ApiResult<List<RouteMissionRecommendationResponse>>> getRouteMissionRecommendationByAttempt(
            @PathVariable(name = "attemptId") Long attemptId
    ) {
        return ResponseEntity.ok(ApiResult.success(recommendationService.getRecommendationsByUserAttempt(attemptId)));
    }

    @Operation(summary = "루트미션 도전기록 등록", description = "루트미션에 대한 도전기록을 등록합니다. 성공 시 SR이 증가합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "도전기록이 성공적으로 등록됨"),
            @ApiResponse(
                    responseCode = "400",
                    description = "유효하지 않은 난이도",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "유효하지 않은 난이도",
                                    value = """
            {
                "errorCode": "VALIDATION_004",
                "message": "유효하지 않은 난이도입니다: V99",
                "timestamp": "2025-07-31T14:20:00",
                "path": "/api/attempts"
            }
            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "루트미션을 찾을 수 없음 또는 활성 세션이 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "루트미션을 찾을 수 없음",
                                            value = """
                {
                    "errorCode": "BUSINESS_007",
                    "message": "루트미션을 찾을 수 없습니다.",
                    "timestamp": "2025-07-31T14:20:00",
                    "path": "/api/attempts"
                }
                """
                                    ),
                                    @ExampleObject(
                                            name = "활성 세션이 없음",
                                            value = """
                {
                    "errorCode": "SESSION_001",
                    "message": "세션을 찾을 수 없습니다.",
                    "timestamp": "2025-07-31T14:20:00",
                    "path": "/api/attempts"
                }
                """
                                    )
                            }
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ApiResult<CreateAttemptResponse>> createAttempt(
            @Valid @RequestBody CreateAttemptRequest request
    ) {
        Long userId = SecurityUtil.getCurrentUserId();
        CreateAttemptResponse response = attemptService.createAttempt(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResult.success("도전기록이 성공적으로 등록되었습니다.", response));
    }

    @Operation(summary = "해당 도전의 영상 업로드 상태 불러오기", description = "해당 도전의 영상 업로드 상태 불러오기", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "해당 도전의 영상 업로드 상태 불러오기")
    @GetMapping("/{attemptId}/upload/status")
    public ResponseEntity<ApiResult<RouteMissionUploadStatusResponse>> getRouteMissionUploadStatus(
            @PathVariable(name = "attemptId") Long attemptId
    ) {
        RouteMissionUploadStatusResponse response = attemptService.getAttemptUploadStatus(attemptId);
        return ResponseEntity.ok(ApiResult.success(response));
    }


    @Operation(summary = "해당 도전의 영상 업로드 세션 생성하기", description = "해당 도전의 영상 업로드 세션 생성하기", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "해당 도전의 영상 업로드 세션 생성하기")
    @PostMapping("/{attemptId}/upload/initialize")
    public ResponseEntity<ApiResult<RouteMissionUploadSessionInitializeResponse>> initializeRouteMissionUploadSession(
            @PathVariable(name = "attemptId") Long attemptId,
            @Valid @RequestBody RouteMissionUploadSessionInitializeRequest request
    ) {
        var response = attemptService.initializeAttemptUploadSession(attemptId, request);

        return ResponseEntity.ok(ApiResult.success(response));
    }


    @Operation(summary = "해당 도전의 영상 청크 업로드", description = "해당 도전의 영상 청크 업로드", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "해당 도전의 영상 청크 업로드하기")
    @PostMapping("/{attemptId}/upload/{uploadId}/chunk")
    public ResponseEntity<ApiResult<RouteMissionUploadChunkResponse>> uploadRouteMissionVideoChunk(
            @PathVariable(name = "attemptId") Long attemptId,
            @PathVariable(name = "uploadId") UUID uploadId,
            @Valid @RequestBody RouteMissionUploadChunkRequest request
            ) {
        return ResponseEntity.ok(ApiResult.success(RouteMissionUploadChunkResponse.builder().build()));
    }


    @Operation(summary = "해당 도전의 영상 업로드 세션 마무리", description = "해당 도전의 영상 업로드 세션 마무리", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "해당 도전의 영상 업로드 세션 마무리")
    @PostMapping("/{attemptId}/upload/{uploadId}/finalize")
    public ResponseEntity<ApiResult<RouteMissionUploadSessionFinalizeResponse>> finalizeRouteMissionUploadSession(
            @PathVariable(name = "attemptId") Long attemptId,
            @PathVariable(name = "uploadId") UUID uploadId
    ) {
        return ResponseEntity.ok(ApiResult.success(RouteMissionUploadSessionFinalizeResponse.builder().build()));
    }
}
