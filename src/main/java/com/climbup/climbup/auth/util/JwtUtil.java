package com.climbup.climbup.auth.util;

import com.climbup.climbup.auth.exception.InvalidTokenException;
import com.climbup.climbup.auth.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private final SecretKey key;
    private final long accessTokenValidityInMilliseconds;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
    }

    public String createAccessToken(Long userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * JWT 토큰을 파싱하여 Claims 반환
     * @param token JWT 토큰
     * @return Claims 객체
     * @throws InvalidTokenException 토큰이 유효하지 않은 경우
     * @throws TokenExpiredException 토큰이 만료된 경우
     */
    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.debug("만료된 JWT 토큰: {}", e.getMessage());
            throw new TokenExpiredException(e);
        } catch (UnsupportedJwtException e) {
            log.debug("지원되지 않는 JWT 토큰: {}", e.getMessage());
            throw new InvalidTokenException("지원되지 않는 토큰 형식", e);
        } catch (MalformedJwtException e) {
            log.debug("잘못된 형식의 JWT 토큰: {}", e.getMessage());
            throw new InvalidTokenException("잘못된 토큰 형식", e);
        } catch (SecurityException e) {
            log.debug("JWT 서명 검증 실패: {}", e.getMessage());
            throw new InvalidTokenException("서명 검증 실패", e);
        } catch (IllegalArgumentException e) {
            log.debug("JWT 토큰이 비어있음: {}", e.getMessage());
            throw new InvalidTokenException("빈 토큰", e);
        }
    }

    /**
     * JWT 토큰에서 사용자 ID 추출
     * @param token JWT 토큰
     * @return 사용자 ID
     * @throws InvalidTokenException 토큰이 유효하지 않거나 사용자 ID가 없는 경우
     */
    public Long getUserId(String token) {
        try {
            Claims claims = parseClaims(token);
            String subject = claims.getSubject();

            if (subject == null || subject.trim().isEmpty()) {
                throw new InvalidTokenException("사용자 ID가 없는 토큰");
            }

            return Long.valueOf(subject);
        } catch (NumberFormatException e) {
            log.debug("JWT 토큰의 subject를 Long으로 변환 실패: {}", e.getMessage());
            throw new InvalidTokenException("잘못된 사용자 ID 형식", e);
        }
    }

    /**
     * 토큰 유효성 검사 (예외를 던지지 않고 boolean 반환)
     * Filter에서 사용하기 위한 메서드
     * @param token JWT 토큰
     * @return 토큰 유효성 여부
     */
    public boolean isTokenValid(String token) {
        try {
            Claims claims = parseClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (TokenExpiredException e) {
            log.debug("토큰 만료: {}", e.getMessage());
            return false;
        } catch (InvalidTokenException e) {
            log.debug("유효하지 않은 토큰: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("JWT 토큰 검증 중 예상치 못한 오류: {}", e.getClass().getSimpleName());
            return false;
        }
    }

    /**
     * 토큰 유효성 검사 (예외를 던지는 버전)
     * Controller나 Service에서 사용하기 위한 메서드
     * @param token JWT 토큰
     * @throws InvalidTokenException 토큰이 유효하지 않은 경우
     * @throws TokenExpiredException 토큰이 만료된 경우
     */
    public void validateToken(String token) {
        Claims claims = parseClaims(token); // 내부에서 적절한 예외를 던짐

        if (claims.getExpiration().before(new Date())) {
            throw new TokenExpiredException();
        }
    }
}