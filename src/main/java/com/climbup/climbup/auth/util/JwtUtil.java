package com.climbup.climbup.auth.util;

import com.climbup.climbup.auth.exception.InvalidTokenException;
import com.climbup.climbup.auth.exception.TokenExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private final SecretKey key;
    private final long accessTokenValidityInSeconds;
    private final long refreshTokenValidityInSeconds;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.access-token-validity-in-seconds:3600}") long accessTokenValidityInSeconds,
                   @Value("${jwt.refresh-token-validity-in-seconds:604800}") long refreshTokenValidityInSeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
    }

    public String createAccessToken(Long userId) {
        return createToken(userId, accessTokenValidityInSeconds, "ACCESS");
    }

    public String createRefreshToken(Long userId) {
        return createToken(userId, refreshTokenValidityInSeconds, "REFRESH");
    }

    private String createToken(Long userId, long validityInSeconds, String tokenType) {
        Instant now = Instant.now();
        Instant validity = now.plus(validityInSeconds, ChronoUnit.SECONDS);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("type", tokenType)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(validity))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("토큰이 만료되었습니다");
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다");
        }
    }

    public Long getUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return Long.valueOf(claims.getSubject());
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("토큰이 만료되었습니다");
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다");
        }
    }

    public String getTokenType(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("type", String.class);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("토큰이 만료되었습니다");
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다");
        }
    }

    public boolean isAccessToken(String token) {
        return "ACCESS".equals(getTokenType(token));
    }

    public boolean isRefreshToken(String token) {
        return "REFRESH".equals(getTokenType(token));
    }
}