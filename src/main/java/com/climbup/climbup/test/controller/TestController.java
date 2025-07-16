package com.climbup.climbup.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/test")
public class TestController {
    @Operation(summary = "랜덤 숫자 생성", description = "0-99 사이의 랜덤한 정수를 반환합니다")
    @ApiResponse(responseCode = "200", description = "성공적으로 랜덤 숫자를 생성함")
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getRandomNumber() {
        Map<String, Object> response = new HashMap<>();
        response.put("randomNumber", (int)(Math.random() * 100));
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
