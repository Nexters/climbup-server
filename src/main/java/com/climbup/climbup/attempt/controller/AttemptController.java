package com.climbup.climbup.attempt.controller;


import com.climbup.climbup.attempt.dto.request.CreateAttemptRequest;
import com.climbup.climbup.attempt.dto.response.*;
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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/attempts")
@Tag(name = "Attempts", description = "루트 미션 도전 관련 API")
@RequiredArgsConstructor
public class AttemptController {

    private final AttemptService attemptService;
    private final RecommendationService recommendationService;


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
        RouteMissionUploadSessionInitializeResponse response = attemptService.initializeAttemptUploadSession(attemptId, request);

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

        RouteMissionUploadChunkResponse response = attemptService.uploadChunk(uploadId, request);

        return ResponseEntity.ok(ApiResult.success(response));
    }


    @Operation(summary = "해당 도전의 영상 업로드 세션 마무리", description = "해당 도전의 영상 업로드 세션을 마무리하고 썸네일을 함께 업로드합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "해당 도전의 영상 업로드 세션 마무리")
    @PostMapping(value = "/{attemptId}/upload/{uploadId}/finalize", consumes = "multipart/form-data")
    public ResponseEntity<ApiResult<RouteMissionUploadSessionFinalizeResponse>> finalizeRouteMissionUploadSession(
            @PathVariable(name = "attemptId") Long attemptId,
            @PathVariable(name = "uploadId") UUID uploadId,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnailFile
    ) {
        RouteMissionUploadSessionFinalizeResponse response = attemptService.finalizeUploadSession(uploadId, thumbnailFile);
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @Operation(summary = "세션별 도전기록 조회",
            description = "특정 세션의 도전기록을 성공/실패로 구분하여 조회합니다.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "세션 도전기록 조회 성공")
    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<ApiResult<SessionAttemptResponse>> getSessionAttempts(
            @PathVariable(name = "sessionId") Long sessionId
    ) {
        Long userId = SecurityUtil.getCurrentUserId();
        SessionAttemptResponse response = attemptService.getSessionAttempts(userId, sessionId);

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "성공한 도전 기록 조회",
            description = "사용자가 성공한 모든 도전 기록들을 최신순으로 조회합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<ApiResult<Page<AttemptResponse>>> getAttempts(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "암장 ID (선택사항 - 입력 시 해당 암장만 필터링)", example = "1")
            @RequestParam(required = false) Long gymId,

            @Parameter(description = "성공 여부 (기본값: true)", example = "true")
            @RequestParam(defaultValue = "true") boolean success
    ) {
        Long userId = SecurityUtil.getCurrentUserId();

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<AttemptResponse> response = attemptService.getAttempts(userId, gymId, success, pageable);

        String message = success ? "성공한 도전 기록이 조회되었습니다." : "실패한 도전 기록이 조회되었습니다.";
        return ResponseEntity.ok(
                ApiResult.success(message, response)
        );
    }

    @Operation(summary = "특정 도전 기록의 상태 조회", description = "도전 기록의 현재 상태를 확인합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "도전 기록 상태 조회 성공")
    @GetMapping("/{attemptId}/status")
    public ResponseEntity<ApiResult<AttemptStatusResponse>> getAttemptStatus(
            @PathVariable(name = "attemptId") Long attemptId
    ) {
        AttemptStatusResponse response = attemptService.getAttemptStatus(attemptId);
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @Operation(summary = "업로드 미완료 도전 기록 목록 조회", description = "사용자의 업로드가 완료되지 않은 도전 기록들을 조회합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "미완료 도전 기록 목록 조회 성공")
    @GetMapping("/incomplete")
    public ResponseEntity<ApiResult<List<UserMissionAttemptResponse>>> getIncompleteAttempts() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<UserMissionAttemptResponse> response = attemptService.getIncompleteAttempts(userId);
        return ResponseEntity.ok(ApiResult.success(response));
    }
}
