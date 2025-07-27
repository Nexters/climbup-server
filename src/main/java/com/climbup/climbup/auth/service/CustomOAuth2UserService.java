package com.climbup.climbup.auth.service;

import com.climbup.climbup.auth.dto.CustomOAuth2User;
import com.climbup.climbup.auth.dto.KakaoOAuth2UserInfo;
import com.climbup.climbup.auth.util.RandomNicknameGenerator;
import com.climbup.climbup.user.entity.User;
import com.climbup.climbup.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        log.info("OAuth2 사용자 정보 조회 완료: {}", oauth2User.getAttributes());

        try {
            KakaoOAuth2UserInfo userInfo = new KakaoOAuth2UserInfo(oauth2User.getAttributes());
            User user = findOrCreateUser(userInfo);
            return new CustomOAuth2User(user, oauth2User.getAttributes());
        } catch (Exception e) {
            log.error("사용자 생성 중 예외 발생", e);
            throw e;
        }
    }

    private User findOrCreateUser(KakaoOAuth2UserInfo userInfo) {
        return userRepository.findByKakaoId(userInfo.getId())
                .orElseGet(() -> createUser(userInfo));
    }

    private User createUser(KakaoOAuth2UserInfo userInfo) {
        String nickname = generateUniqueNickname();

        User user = User.builder()
                .kakaoId(userInfo.getId())
                .name(userInfo.getName())
                .nickname(nickname)
                .imageUrl(userInfo.getProfileImageUrl())
                .sr(600)
                .level(null)
                .gym(null)
                .build();

        return userRepository.save(user);
    }

    private String generateUniqueNickname() {
        String nickname;
        int tryCount = 0;
        do {
            nickname = RandomNicknameGenerator.generate();
            tryCount++;
            if (tryCount > 50) throw new RuntimeException("랜덤 닉네임 생성 실패: 중복이 너무 많습니다.");
        } while (userRepository.existsByNickname(nickname));
        return nickname;
    }
}