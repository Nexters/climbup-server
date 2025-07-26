package com.climbup.climbup.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "OAuth2 Redirect", description = "암장 관련 API")
@RestController
@Slf4j
public class OAuth2RedirectController {

    @Operation(summary = "oauth2 리다이렉트 경로", description = "로그인 성공 후 리다이렉트되는 경로입니다")
    @GetMapping("/oauth2/redirect")
    public ResponseEntity<Map<String, Object>> handleOAuth2Redirect(@RequestParam String token) {
        log.info("JWT 토큰 발급 완료");

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "로그인이 성공적으로 완료되었습니다!",
                "token", token,
                "instruction", "이 토큰을 Authorization 헤더에 'Bearer ' + token 형태로 사용하세요"
        ));
    }
}