package com.climbup.climbup.global.discord;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "discord.enabled", havingValue = "true")
public class DiscordService {

    private final DiscordConfig discordConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private RestTemplate restTemplate;

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @PostConstruct
    public void initRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);  // 5초
        factory.setReadTimeout(10000);    // 10초

        this.restTemplate = new RestTemplate(factory);

        log.info("Discord RestTemplate 초기화 완료 (connect: 5s, read: 10s)");
    }

    @Async
    public void sendErrorNotification(String errorMessage, String className, String methodName) {
        String sanitizedMessage = sanitizeErrorMessage(errorMessage);

        if (!discordConfig.isEnabled() ||
                !isValidWebhookUrl(discordConfig.getWebhook().getErrorUrl())) {
            return;
        }

        try {
            Map<String, Object> embed = createErrorEmbed(sanitizedMessage, className, methodName);
            Map<String, Object> payload = Map.of(
                    "embeds", List.of(embed)
            );

            sendWebhookMessage(discordConfig.getWebhook().getErrorUrl(), payload);
            log.debug("Discord 에러 알림 전송 완료");
        } catch (Exception e) {
            log.error("Discord 에러 알림 전송 실패", e);
        }
    }

    @Async
    public void sendUserRegistrationNotification(String nickname, long totalUserCount) {
        if (!discordConfig.isEnabled() ||
                !isValidWebhookUrl(discordConfig.getWebhook().getNotificationUrl()) ||
                !"prod".equals(activeProfile)) {
            return;
        }

        try {
            Map<String, Object> embed = createRegistrationEmbed(nickname, totalUserCount);
            Map<String, Object> payload = Map.of(
                    "embeds", List.of(embed)
            );

            sendWebhookMessage(discordConfig.getWebhook().getNotificationUrl(), payload);
            log.debug("Discord 회원가입 알림 전송 완료");
        } catch (Exception e) {
            log.error("Discord 회원가입 알림 전송 실패", e);
        }
    }

    private Map<String, Object> createErrorEmbed(String errorMessage, String className, String methodName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Map<String, Object> embed = new HashMap<>();
        embed.put("title", "🚨 " + activeProfile.toUpperCase() + " 환경 에러 발생");
        embed.put("color", "prod".equals(activeProfile) ? 15158332 : 16776960);
        embed.put("timestamp", LocalDateTime.now().toString());

        Map<String, Object> field1 = Map.of(
                "name", "📍 발생 위치",
                "value", String.format("**Class:** %s\n**Method:** %s", className, methodName),
                "inline", false
        );

        Map<String, Object> field2 = Map.of(
                "name", "💥 에러 내용",
                "value", "```\n" + truncateMessage(errorMessage, 1000) + "\n```",
                "inline", false
        );

        Map<String, Object> field3 = Map.of(
                "name", "⏰ 발생 시간",
                "value", timestamp,
                "inline", true
        );

        embed.put("fields", List.of(field1, field2, field3));
        return embed;
    }

    private boolean isValidWebhookUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        return url.startsWith("https://discord.com/api/webhooks/") ||
                url.startsWith("https://discordapp.com/api/webhooks/");
    }

    private String sanitizeErrorMessage(String message) {
        if (message == null) return null;

        String sanitized = message
                .replaceAll("(?i)(password|token|secret|key)[\\s=:]+[^\\s\\n]+", "$1=***")
                .replaceAll("(?i)(authorization:?\\s+bearer\\s+)[^\\s\\n]+", "$1***")
                .replaceAll("(?i)(api[_-]?key[\\s=:]+)[^\\s\\n]+", "$1***");

        return truncateMessage(sanitized, 1000);
    }

    private Map<String, Object> createRegistrationEmbed(String nickname, long totalUserCount) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Map<String, Object> embed = new HashMap<>();
        embed.put("title", "🎉 새로운 회원 가입!");
        embed.put("color", 5763719);
        embed.put("timestamp", LocalDateTime.now().toString());

        Map<String, Object> field1 = Map.of(
                "name", "👤 가입자",
                "value", nickname,
                "inline", true
        );

        Map<String, Object> field2 = Map.of(
                "name", "📊 누적 가입자 수",
                "value", String.format("**%,d명**", totalUserCount),
                "inline", true
        );

        Map<String, Object> field3 = Map.of(
                "name", "⏰ 가입 시간",
                "value", timestamp,
                "inline", false
        );

        embed.put("fields", List.of(field1, field2, field3));
        return embed;
    }

    private void sendWebhookMessage(String webhookUrl, Map<String, Object> payload) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String jsonPayload = objectMapper.writeValueAsString(payload);
            HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

            restTemplate.postForEntity(webhookUrl, entity, String.class);

        } catch (Exception e) {
            log.error("Discord 알림 전송 실패", e);
        }
    }

    private String truncateMessage(String message, int maxLength) {
        if (message == null || message.length() <= maxLength) {
            return message;
        }
        return message.substring(0, maxLength) + "...";
    }
}