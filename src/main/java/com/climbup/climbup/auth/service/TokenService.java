package com.climbup.climbup.auth.service;

import com.climbup.climbup.auth.dto.TokenResponse;
import com.climbup.climbup.auth.exception.InvalidTokenException;
import com.climbup.climbup.auth.util.JwtUtil;
import com.climbup.climbup.user.exception.UserNotFoundException;
import com.climbup.climbup.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60; // 7일

    public TokenResponse createTokens(Long userId) {
        // 사용자 존재 여부 확인
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);

        // Refresh Token을 Redis에 저장
        storeRefreshToken(userId, refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    public TokenResponse refreshTokens(String refreshToken) {
        // Refresh Token 유효성 검증
        if (!jwtUtil.isTokenValid(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
            throw new InvalidTokenException("유효하지 않은 refresh token입니다");
        }

        Long userId = jwtUtil.getUserId(refreshToken);

        // Redis에서 저장된 Refresh Token과 비교
        String storedRefreshToken = getStoredRefreshToken(userId);
        if (!refreshToken.equals(storedRefreshToken)) {
            throw new InvalidTokenException("refresh token이 일치하지 않습니다");
        }

        // 새로운 토큰 발급
        return createTokens(userId);
    }

    public void revokeRefreshToken(Long userId) {
        deleteStoredRefreshToken(userId);
        log.info("Refresh token revoked for userId: {}", userId);
    }

    private void storeRefreshToken(Long userId, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    private String getStoredRefreshToken(Long userId) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        return redisTemplate.opsForValue().get(key);
    }

    private void deleteStoredRefreshToken(Long userId) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        redisTemplate.delete(key);
    }
}