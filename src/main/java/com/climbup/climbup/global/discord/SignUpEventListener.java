package com.climbup.climbup.global.discord;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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

    private final EntityManager entityManager;

    @EventListener
    public void handleUserRegistration(SignUpEvent event) {
        if (discordService == null) {
            return;
        }

        try {
            // 전체 사용자 수 조회
            Query query = entityManager.createQuery("SELECT COUNT(u) FROM User u");
            long totalUserCount = (Long) query.getSingleResult();

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