package com.climbup.climbup.auth.handler;

import com.climbup.climbup.auth.dto.CustomOAuth2User;
import com.climbup.climbup.auth.dto.TokenResponse;
import com.climbup.climbup.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;

    @Value("${app.oauth2.authorized-redirect-uris}")
    private String authorizedRedirectUris;

    @Value("${app.oauth2.default-redirect-uri}")
    private String defaultRedirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        try {
            CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
            log.info("✅ 로그인 성공: userId={}", oauth2User.getUserId());

            // 토큰 생성
            TokenResponse tokenResponse = tokenService.createTokens(oauth2User.getUserId());

            // 저장된 redirect URI 가져오기
            String targetRedirectUri = determineTargetUrl(request);

            String targetUrl = targetRedirectUri +
                    "?access_token=" + URLEncoder.encode(tokenResponse.getAccessToken(), StandardCharsets.UTF_8) +
                    "&refresh_token=" + URLEncoder.encode(tokenResponse.getRefreshToken(), StandardCharsets.UTF_8) +
                    "&token_type=" + URLEncoder.encode(tokenResponse.getTokenType(), StandardCharsets.UTF_8);

            clearAuthenticationAttributes(request);

            getRedirectStrategy().sendRedirect(request, response, targetUrl);

        } catch (Exception e) {
            log.error("OAuth2 인증 처리 중 오류 발생: {}", e.getClass().getSimpleName(), e);
            String errorUrl = defaultRedirectUri + "?error=auth_failed";
            response.sendRedirect(errorUrl);
        }
    }

    private String determineTargetUrl(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String savedRedirectUri = null;

        if (session != null) {
            savedRedirectUri = (String) session.getAttribute("redirect_uri");
            session.removeAttribute("redirect_uri");
        }

        if (StringUtils.hasText(savedRedirectUri) && isAuthorizedRedirectUri(savedRedirectUri)) {
            return savedRedirectUri;
        }

        return defaultRedirectUri;
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        List<String> authorizedUris = Arrays.asList(authorizedRedirectUris.split(","));
        return authorizedUris.stream().anyMatch(authorizedUri ->
                uri.toLowerCase().startsWith(authorizedUri.toLowerCase().trim()));
    }
}