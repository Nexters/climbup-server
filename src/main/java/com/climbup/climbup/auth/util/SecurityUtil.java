package com.climbup.climbup.auth.util;

import com.climbup.climbup.auth.dto.CustomOAuth2User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Spring Security 컨텍스트에서 현재 인증된 사용자 정보를 가져오는 유틸리티
 */
@Slf4j
public class SecurityUtil {

    /**
     * 현재 인증된 사용자의 ID 반환
     *
     * @return 사용자 ID, 인증되지 않은 경우 null
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof Long) {
                return (Long) principal;
            } else if (principal instanceof CustomOAuth2User customUser) {
                return customUser.getUserId();
            } else if (principal instanceof String str && str.equals("anonymousUser")) {
                log.debug("익명 사용자입니다.");
            } else {
                log.warn("예상하지 못한 Principal 타입: {}", principal.getClass().getName());
            }
        }

        return null;
    }

    /**
     * 현재 사용자가 인증되었는지 확인
     *
     * @return 인증 여부
     */
    public static boolean isAuthenticated() {
        return getCurrentUserId() != null;
    }
}