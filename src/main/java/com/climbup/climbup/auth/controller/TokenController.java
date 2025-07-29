package com.climbup.climbup.auth.controller;

import com.climbup.climbup.auth.dto.TokenResponse;
import com.climbup.climbup.auth.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Token Management", description = "토큰 관리 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class TokenController {

    private final TokenService tokenService;

    @Operation(summary = "토큰 재발급", description = "Refresh Token으로 새로운 Access Token을 발급받습니다")
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        TokenResponse tokenResponse = tokenService.refreshTokens(refreshToken);

        return ResponseEntity.ok(tokenResponse);
    }

    @Operation(summary = "로그아웃", description = "Refresh Token을 무효화합니다")
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Long userId) {
            tokenService.revokeRefreshToken(userId);
        }

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "로그아웃이 완료되었습니다"
        ));
    }
}