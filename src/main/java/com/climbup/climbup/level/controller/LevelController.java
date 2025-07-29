package com.climbup.climbup.level.controller;

import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.level.dto.response.LevelResponse;
import com.climbup.climbup.level.service.LevelService;
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

@Tag(name = "Level", description = "난이도 관련 API")
@RestController
@RequestMapping("/api/levels")
@RequiredArgsConstructor
public class LevelController {

    private final LevelService levelService;

    @Operation(summary = "모든 레벨 반환", description = "존재하는 모든 레벨 리스트를 반환합니다")
    @ApiResponse(responseCode = "200", description = "성공적으로 모든 레벨 종류의 리스트를 반환")
    @GetMapping
    public ResponseEntity<ApiResult<List<LevelResponse>>> getAllLevelList() {
        return ResponseEntity.ok(ApiResult.success(levelService.getAllLevels()));
    }

    @Operation(summary = "특정 레벨 정보 조회", description = "레벨 ID로 특정 레벨의 상세 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "레벨 정보 조회 성공")
    @GetMapping("/{levelId}")
    public ResponseEntity<ApiResult<LevelResponse>> getLevelById(
            @Parameter(description = "레벨 ID") @PathVariable Long levelId) {
        return ResponseEntity.ok(ApiResult.success(levelService.getLevelById(levelId)));
    }

}
