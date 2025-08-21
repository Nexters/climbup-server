package com.climbup.climbup.auth.filter;

import com.climbup.climbup.auth.exception.InvalidTokenException;
import com.climbup.climbup.auth.exception.TokenExpiredException;
import com.climbup.climbup.auth.util.JwtUtil;
import com.climbup.climbup.user.exception.UserNotFoundException;
import com.climbup.climbup.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT 토큰 기반 인증 필터
 * 모든 HTTP 요청에서 JWT 토큰을 검증하고 Spring Security 컨텍스트에 인증 정보를 설정
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = extractTokenFromRequest(request);

        if (StringUtils.hasText(token)) {
            try {
                if (jwtUtil.isTokenValid(token) && jwtUtil.isAccessToken(token)) {
                    authenticateUser(request, token);
                }
            } catch (TokenExpiredException | InvalidTokenException e) {
                log.debug("토큰 검증 실패: {}", e.getMessage());
            } catch (UserNotFoundException e) {
                log.warn("토큰은 유효하지만 사용자를 찾을 수 없음: {}", e.getMessage());
            } catch (Exception e) {
                log.error("JWT 인증 처리 중 예상치 못한 오류 발생: {}", e.getClass().getSimpleName(), e);
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 사용자 인증 처리
     * @param request HTTP 요청
     * @param token JWT 토큰
     */
    private void authenticateUser(HttpServletRequest request, String token) {
        Long userId = jwtUtil.getUserId(token);

    var user = userRepository.findById(userId)
            .orElseThrow(() -> {
                log.error("사용자 조회 실패: userId={}", userId);
                return new UserNotFoundException(userId);
            });

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.debug("JWT 인증 성공: userId={}", userId);
    }

    /**
     * HTTP 요청 헤더에서 JWT 토큰 추출
     * Authorization: Bearer {token} 형식에서 토큰 부분만 추출
     * @param request HTTP 요청
     * @return JWT 토큰 (없으면 null)
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    /**
     * 특정 요청에 대해 필터를 적용하지 않을 조건 설정
     * @param request HTTP 요청
     * @return 필터 적용 제외 여부
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        return path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/") ||
                path.startsWith("/favicon.ico") ||
                path.startsWith("/oauth2/") ||
                path.startsWith("/login/") ||
                path.startsWith("/api/auth/refresh") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/api/test/");
    }
}