package com.climbup.climbup.brand.controller;

import com.climbup.climbup.brand.dto.response.BrandResponse;
import com.climbup.climbup.brand.service.BrandService;
import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.gym.dto.response.GymLevelResponse;
import com.climbup.climbup.gym.dto.response.GymResponse;
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

@Tag(name = "Brand", description = "암장 브랜드 관련 API")
@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @Operation(summary = "모든 브랜드 조회", description = "등록된 모든 암장 브랜드를 조회합니다")
    @ApiResponse(responseCode = "200", description = "브랜드 리스트 조회 성공")
    @GetMapping
    public ResponseEntity<ApiResult<List<BrandResponse>>> getAllBrands() {
        return ResponseEntity.ok(ApiResult.success(brandService.getAllBrands()));
    }

    @Operation(summary = "브랜드별 암장 리스트 조회", description = "특정 브랜드의 모든 암장을 조회합니다")
    @ApiResponse(responseCode = "200", description = "브랜드별 암장 리스트 조회 성공")
    @GetMapping("/{brandId}/gyms")
    public ResponseEntity<ApiResult<List<GymResponse>>> getBrandGyms(
            @Parameter(description = "브랜드 ID") @PathVariable Long brandId) {
        return ResponseEntity.ok(ApiResult.success(brandService.getBrandGyms(brandId)));
    }

    @Operation(summary = "브랜드별 레벨 리스트 조회", description = "특정 브랜드에서 사용하는 모든 레벨을 조회합니다")
    @ApiResponse(responseCode = "200", description = "브랜드별 레벨 리스트 조회 성공")
    @GetMapping("/{brandId}/levels")
    public ResponseEntity<ApiResult<List<GymLevelResponse>>> getBrandLevels(
            @Parameter(description = "브랜드 ID") @PathVariable Long brandId) {
        return ResponseEntity.ok(ApiResult.success(brandService.getBrandLevels(brandId)));
    }
}