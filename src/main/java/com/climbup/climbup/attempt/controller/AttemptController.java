package com.climbup.climbup.attempt.controller;

import com.climbup.climbup.attempt.dto.request.RouteMissionUploadChunkRequest;
import com.climbup.climbup.attempt.dto.request.RouteMissionUploadSessionInitializeRequest;
import com.climbup.climbup.attempt.dto.response.RouteMissionUploadChunkResponse;
import com.climbup.climbup.attempt.dto.response.RouteMissionUploadSessionFinalizeResponse;
import com.climbup.climbup.attempt.dto.response.RouteMissionUploadSessionInitializeResponse;
import com.climbup.climbup.attempt.dto.response.RouteMissionUploadStatusResponse;
import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.recommendation.dto.request.RouteMissionRecommendationResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attempts")
@Tag(name = "Attempts", description = "루트 미션 도전 관련 API")
@RequiredArgsConstructor
public class AttemptController {

    @Operation(summary = "도전한 루트미션과 비슷한 난이도의 루트미션 리스트 불러오기", description = "도전한 루트미션과 비슷한 난이도의 루트미션 리스트를 받아보기", security = @SecurityRequirement(name = "bearerAuth"))
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
    @GetMapping("/{attemptId}/recommendations")
    public ResponseEntity<ApiResult<List<RouteMissionRecommendationResponse>>> getRouteMissionRecommendationByAttempt(
            @PathVariable(name = "attemptId") Long attemptId
    ) {

        return ResponseEntity.ok(ApiResult.success(List.of()));
    }


    @GetMapping("/{attemptId}/upload/status")
    public ResponseEntity<ApiResult<RouteMissionUploadStatusResponse>> getRouteMissionUploadStatus(
            @PathVariable(name = "attemptId") Long attemptId
    ) {
        return ResponseEntity.ok(ApiResult.success(RouteMissionUploadStatusResponse.builder().build()));
    }

    @PostMapping("/{attemptId}/upload/initialize")
    public ResponseEntity<ApiResult<RouteMissionUploadSessionInitializeResponse>> initializeRouteMissionUploadSession(
            @PathVariable(name = "attemptId") Long attemptId,
            @Valid @RequestBody RouteMissionUploadSessionInitializeRequest request
    ) {
        return ResponseEntity.ok(ApiResult.success(RouteMissionUploadSessionInitializeResponse.builder().build()));
    }


    @PostMapping("/{attemptId}/upload/{uploadId}/chunk")
    public ResponseEntity<ApiResult<RouteMissionUploadChunkResponse>> uploadRouteMissionVideoChunk(
            @PathVariable(name = "attemptId") Long attemptId,
            @PathVariable(name = "uploadId") UUID uploadId,
            @Valid @RequestBody RouteMissionUploadChunkRequest request
            ) {
        return ResponseEntity.ok(ApiResult.success(RouteMissionUploadChunkResponse.builder().build()));
    }


    @PostMapping("/{attemptId}/upload/{uploadId}/finalize")
    public ResponseEntity<ApiResult<RouteMissionUploadSessionFinalizeResponse>> finalizeRouteMissionUploadSession(
            @PathVariable(name = "attemptId") Long attemptId,
            @PathVariable(name = "uploadId") UUID uploadId
    ) {
        return ResponseEntity.ok(ApiResult.success(RouteMissionUploadSessionFinalizeResponse.builder().build()));
    }
}
