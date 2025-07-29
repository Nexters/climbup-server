package com.climbup.climbup.auth.controller;

import com.climbup.climbup.common.exception.CommonBusinessException;
import com.climbup.climbup.common.exception.ErrorCode;
import com.climbup.climbup.common.exception.ValidationException;
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
            @RequestParam(required = false) String token,
            @RequestParam(required = false) String error) {

        if (error != null) {
            log.warn("OAuth2 인증 실패: {}", error);
            switch (error) {
                case "access_denied":
                    throw new CommonBusinessException(ErrorCode.ACCESS_DENIED);
                case "invalid_request":
                    throw new ValidationException(ErrorCode.VALIDATION_ERROR, error);
                default:
                    throw new CommonBusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }

        log.info("JWT 토큰 발급 완료");

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

        if (token != null) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "로그인이 성공적으로 완료되었습니다!",
                    "token", token,
                    "instruction", "이 토큰을 Authorization 헤더에 'Bearer ' + token 형태로 사용하세요"
            ));
        }

        throw new ValidationException(ErrorCode.REQUIRED_FIELD_MISSING, "token");
    }
}