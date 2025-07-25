package com.climbup.climbup.level.controller;

import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.level.dto.response.LevelResponse;
import com.climbup.climbup.level.service.LevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
