package com.climbup.climbup.global.discord;

import com.climbup.climbup.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "discord.enabled", havingValue = "true")
public class SignUpEventListener {

    private final DiscordService discordService;
    private final UserRepository userRepository;

    @EventListener
    public void handleUserRegistration(SignUpEvent event) {
        if (discordService == null) {
            return;
        }

        try {
            long totalUserCount = userRepository.count();

            discordService.sendUserRegistrationNotification(
                    event.getNickname(),
                    totalUserCount
            );

            log.info("회원가입 Discord 알림 전송: nickname={}, totalCount={}",
                    event.getNickname(), totalUserCount);

        } catch (Exception e) {
            log.error("회원가입 Discord 알림 처리 중 오류 발생", e);
        }
    }
}