package com.climbup.climbup.gym.controller;

import com.climbup.climbup.gym.dto.response.GymResponse;
import com.climbup.climbup.gym.service.GymService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Climbing Gym", description = "암장 관련 API")
@RestController
@RequestMapping("/api/gyms")
@RequiredArgsConstructor
public class GymController {

    private final GymService gymService;

    @Operation(summary = "모든 암장 반환", description = "존재하는 모든 암장의 리스트를 반환합니다")
    @ApiResponse(responseCode = "200", description = "성공적으로 모든 암장 리스트를 반환")
    @GetMapping
    public ResponseEntity<List<GymResponse>> getAllGyms() {
        return ResponseEntity.ok(gymService.getAllGyms());
    }
}