package com.climbup.climbup.auth.service;

import com.climbup.climbup.auth.dto.CustomOAuth2User;
import com.climbup.climbup.auth.dto.KakaoOAuth2UserInfo;
import com.climbup.climbup.auth.exception.NicknameGenerationException;
import com.climbup.climbup.auth.util.RandomNicknameGenerator;
import com.climbup.climbup.global.discord.SignUpEvent;
import com.climbup.climbup.user.entity.User;
import com.climbup.climbup.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Value("${auth.nickname.max-retries:50}")
    private int maxRetries;

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("🚀 === CustomOAuth2UserService.loadUser 호출됨 ===");
        log.info("Provider: {}", userRequest.getClientRegistration().getRegistrationId());
        log.info("AccessToken: {}", userRequest.getAccessToken().getTokenValue().substring(0, 10) + "...");
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

        String[] defaultImageUrls = {
                "https://kr.object.ncloudstorage.com/holdy/images/profiles/1755526990460_c0e1f830.png",
                "https://kr.object.ncloudstorage.com/holdy/images/profiles/1755527043121_1787397c.png",
                "https://kr.object.ncloudstorage.com/holdy/images/profiles/1755527087381_fadb90c0.png",
                "https://kr.object.ncloudstorage.com/holdy/images/profiles/1755527100828_869dc21f.png",
                "https://kr.object.ncloudstorage.com/holdy/images/profiles/1755527118289_902514cd.png"
        };

        Random random = new Random();
        String selectedImageUrl = defaultImageUrls[random.nextInt(defaultImageUrls.length)];

        User user = User.builder()
                .kakaoId(userInfo.getId())
                .name(userInfo.getName())
                .nickname(nickname)
                .imageUrl(selectedImageUrl)
                .sr(600)
                .gymLevel(null)
                .gym(null)
                .build();

        User savedUser = userRepository.save(user);

        // userRepository.flush();

        log.info("새 사용자 생성 완료: id={}, kakaoId={}", savedUser.getId(), savedUser.getKakaoId());

        // eventPublisher.publishEvent(new SignUpEvent(this, nickname, savedUser.getId()));

        return savedUser;
    }

    private String generateUniqueNickname() {
        for (int i = 0; i < maxRetries; i++) {
            String nickname = RandomNicknameGenerator.generate();
            if (!userRepository.existsByNickname(nickname)) {
                return nickname;
            }
        }
        throw new NicknameGenerationException();
    }
}