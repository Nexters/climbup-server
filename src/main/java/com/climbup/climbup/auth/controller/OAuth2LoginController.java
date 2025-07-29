package com.climbup.climbup.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Tag(name = "OAuth2 Login", description = "OAuth2 로그인 시작")
@Controller
@Slf4j
public class OAuth2LoginController {

    @Value("${app.oauth2.authorized-redirect-uris}")
    private String authorizedRedirectUris;

    @Operation(summary = "카카오 로그인 시작", description = "카카오 OAuth2 로그인을 시작합니다")
    @GetMapping("/login/kakao")
    public String startKakaoLogin(@RequestParam(required = false) String redirect_uri,
                                  HttpServletRequest request) {

        if (StringUtils.hasText(redirect_uri) && isAuthorizedRedirectUri(redirect_uri)) {
            HttpSession session = request.getSession();
            session.setAttribute("redirect_uri", redirect_uri);
            log.info("Redirect URI 저장: {}", redirect_uri);
        }

        return "redirect:/oauth2/authorization/kakao";
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        List<String> authorizedUris = Arrays.asList(authorizedRedirectUris.split(","));
        return authorizedUris.stream().anyMatch(authorizedUri ->
                uri.equals(authorizedUri.trim()));
    }
}