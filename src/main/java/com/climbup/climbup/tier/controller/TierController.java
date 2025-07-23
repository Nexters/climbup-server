package com.climbup.climbup.tier.controller;

import com.climbup.climbup.tier.dto.response.TierResponse;
import com.climbup.climbup.tier.service.TierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tiers")
@RequiredArgsConstructor
public class TierController {

    private final TierService tierService;

    @Operation(summary = "모든 티어 반환", description = "존재하는 모든 티어의 리스트를 반환합니다")
    @ApiResponse(responseCode = "200", description = "성공적으로 모든 티어 종류의 리스트를 반환")
    @GetMapping
    public ResponseEntity<List<TierResponse>> getAllTierList() {
        return ResponseEntity.ok(tierService.getAllTiers());
    }

}
