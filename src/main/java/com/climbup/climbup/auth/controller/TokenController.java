package com.climbup.climbup.auth.controller;

import com.climbup.climbup.auth.dto.TokenResponse;
import com.climbup.climbup.auth.exception.InvalidTokenException;
import com.climbup.climbup.auth.service.TokenService;
import com.climbup.climbup.auth.util.JwtUtil;
import com.climbup.climbup.auth.util.SecurityUtil;
import com.climbup.climbup.common.exception.CommonBusinessException;
import com.climbup.climbup.common.exception.ErrorCode;
import com.climbup.climbup.common.exception.ValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Token Management", description = "토큰 관리 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class TokenController {

    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "토큰 재발급", description = "Refresh Token으로 새로운 Access Token을 발급받습니다")
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new ValidationException(ErrorCode.REQUIRED_FIELD_MISSING, "refreshToken");
        }

        Long tokenUserId = jwtUtil.getUserId(refreshToken);

        String userId = request.get("userId");
        if (userId != null && !userId.equals(String.valueOf(tokenUserId))) {
            log.warn("토큰의 사용자 ID와 요청된 사용자 ID 불일치: token={}, requested={}", tokenUserId, userId);
            throw new InvalidTokenException();
        }

        TokenResponse tokenResponse = tokenService.refreshTokens(refreshToken);
        log.info("토큰 재발급 성공: userId={}", tokenUserId);

        return ResponseEntity.ok(tokenResponse);
    }

    @Operation(summary = "로그아웃", description = "Refresh Token을 무효화합니다")
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {

        Long userId = SecurityUtil.getCurrentUserId();

        if (userId == null) {
            log.warn("인증되지 않은 사용자의 로그아웃 시도");
            throw new CommonBusinessException(ErrorCode.ACCESS_DENIED);
        }

        tokenService.revokeRefreshToken(userId);
        log.info("사용자 {}의 refresh token 무효화 완료", userId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "로그아웃이 완료되었습니다."
        ));
    }
}