package com.climbup.climbup.auth.handler;

import com.climbup.climbup.auth.dto.CustomOAuth2User;
import com.climbup.climbup.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Value("${app.oauth2.authorized-redirect-uri:http://localhost:9090/auth/callback}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        try {
            CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
            log.info("✅ 로그인 성공: userId={}", oauth2User.getUserId());

            // JWT에는 userId만 포함
            String token = jwtUtil.createAccessToken(oauth2User.getUserId());

            // 프론트엔드 콜백 페이지로 리다이렉트
            String targetUrl = redirectUri + "?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);

            clearAuthenticationAttributes(request);

            getRedirectStrategy().sendRedirect(request, response, targetUrl);

        } catch (Exception e) {
            log.error("❌ OAuth2 성공 핸들러 오류", e);
            String errorUrl = redirectUri.replace("/auth/callback", "/login?error=oauth_error");
            response.sendRedirect(errorUrl);
        }
    }
}