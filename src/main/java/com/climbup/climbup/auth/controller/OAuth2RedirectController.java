package com.climbup.climbup.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class OAuth2RedirectController {

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