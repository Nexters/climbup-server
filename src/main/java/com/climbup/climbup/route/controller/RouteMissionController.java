package com.climbup.climbup.route.controller;

import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.route.dto.request.CreateRouteMissionRequest;
import com.climbup.climbup.route.dto.request.UpdateRouteMissionRequest;
import com.climbup.climbup.route.dto.response.RouteMissionListResponse;
import com.climbup.climbup.route.dto.response.RouteMissionResponse;
import com.climbup.climbup.route.service.RouteMissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/route-missions")
@Tag(name = "Route Missions", description = "루트 미션 관련 API")
@RequiredArgsConstructor
public class RouteMissionController {

    private final RouteMissionService routeMissionService;

    @Operation(summary = "루트미션 생성", description = "새로운 루트미션을 생성합니다. 루트 이미지, 가이드 영상, 썸네일을 함께 업로드합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "루트미션이 성공적으로 생성됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "404", description = "클라이밍장 또는 섹터를 찾을 수 없음")
    })
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResult<RouteMissionResponse>> createRouteMission(
            @Valid @RequestPart("data") CreateRouteMissionRequest request,
            @RequestPart("routeImage") MultipartFile routeImage,
            @RequestPart("guideVideo") MultipartFile guideVideo,
            @RequestPart("videoThumbnail") MultipartFile videoThumbnail
    ) {
        RouteMissionResponse response = routeMissionService.createRouteMission(
                request, routeImage, guideVideo, videoThumbnail
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResult.success("루트미션이 성공적으로 생성되었습니다.", response));
    }

    @Operation(summary = "루트미션 상세 조회", description = "특정 루트미션의 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "루트미션 상세 정보 반환")
    @GetMapping("/{missionId}")
    public ResponseEntity<ApiResult<RouteMissionResponse>> getRouteMission(
            @PathVariable Long missionId
    ) {
        RouteMissionResponse response = routeMissionService.getRouteMission(missionId);
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @Operation(summary = "루트미션 목록 조회", description = "루트미션 목록을 조회합니다. 클라이밍장, 섹터, 난이도로 필터링 가능합니다.")
    @ApiResponse(responseCode = "200", description = "루트미션 목록 반환")
    @GetMapping
    public ResponseEntity<ApiResult<List<RouteMissionListResponse>>> getRouteMissions(
            @Parameter(description = "클라이밍장 ID") @RequestParam(required = false) Long gymId,
            @Parameter(description = "섹터 ID") @RequestParam(required = false) Long sectorId,
            @Parameter(description = "난이도") @RequestParam(required = false) String difficulty
    ) {
        List<RouteMissionListResponse> response = routeMissionService.getRouteMissions(
                gymId, sectorId, difficulty
        );
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @Operation(summary = "루트미션 수정", description = "기존 루트미션을 수정합니다. 파일은 선택적으로 업로드 가능합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "루트미션이 성공적으로 수정됨"),
            @ApiResponse(responseCode = "404", description = "루트미션을 찾을 수 없음")
    })
    @PatchMapping(value = "/{missionId}", consumes = "multipart/form-data")
    public ResponseEntity<ApiResult<RouteMissionResponse>> updateRouteMission(
            @PathVariable Long missionId,
            @Valid @RequestPart("data") UpdateRouteMissionRequest request,
            @RequestPart(value = "routeImage", required = false) MultipartFile routeImage,
            @RequestPart(value = "guideVideo", required = false) MultipartFile guideVideo,
            @RequestPart(value = "videoThumbnail", required = false) MultipartFile videoThumbnail
    ) {
        RouteMissionResponse response = routeMissionService.updateRouteMission(
                missionId, request, routeImage, guideVideo, videoThumbnail
        );

        return ResponseEntity.ok(ApiResult.success("루트미션이 성공적으로 수정되었습니다.", response));
    }

    @Operation(summary = "루트미션 삭제", description = "루트미션을 삭제합니다. (Soft Delete)", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "루트미션이 성공적으로 삭제됨")
    @DeleteMapping("/{missionId}")
    public ResponseEntity<ApiResult<String>> deleteRouteMission(
            @PathVariable Long missionId
    ) {
        routeMissionService.deleteRouteMission(missionId);
        return ResponseEntity.ok(ApiResult.success("루트미션이 성공적으로 삭제되었습니다.", "삭제 완료"));
    }
}