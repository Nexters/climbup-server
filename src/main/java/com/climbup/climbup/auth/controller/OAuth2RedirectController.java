package com.climbup.climbup.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "OAuth2 Redirect", description = "OAuth2 관련 API")
@RestController
@Slf4j
public class OAuth2RedirectController {

    @Operation(summary = "oauth2 리다이렉트 경로", description = "로그인 성공 후 리다이렉트되는 경로입니다")
    @GetMapping("/oauth2/redirect")
    public ResponseEntity<Map<String, Object>> handleOAuth2Redirect(
            @RequestParam(required = false) String access_token,
            @RequestParam(required = false) String refresh_token,
            @RequestParam(required = false) String token_type,
            @RequestParam(required = false) String token,  // 기존 호환성
            @RequestParam(required = false) String error) {

        // 에러 처리
        if (error != null) {
            log.warn("OAuth2 인증 실패: {}", error);
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", error,
                    "message", "로그인 중 오류가 발생했습니다."
            ));
        }

        log.info("JWT 토큰 발급 완료");

        // 새로운 방식 (access_token + refresh_token)
        if (access_token != null && refresh_token != null) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "로그인이 성공적으로 완료되었습니다!",
                    "access_token", access_token,
                    "refresh_token", refresh_token,
                    "token_type", token_type != null ? token_type : "Bearer",
                    "instruction", "Access Token을 Authorization 헤더에 'Bearer ' + token 형태로 사용하세요"
            ));
        }

        // 기존 방식 (단일 token) - 호환성 유지
        if (token != null) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "로그인이 성공적으로 완료되었습니다!",
                    "token", token,
                    "instruction", "이 토큰을 Authorization 헤더에 'Bearer ' + token 형태로 사용하세요"
            ));
        }

        // 토큰이 없는 경우
        return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "error", "missing_token",
                "message", "토큰 정보가 없습니다."
        ));
    }
}