package com.climbup.climbup.global.discord;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "discord")
@Getter
@Setter
public class DiscordConfig {

    private Webhook webhook = new Webhook();
    private boolean enabled = false;

    @Getter
    @Setter
    public static class Webhook {
        private String errorUrl;
        private String notificationUrl;
    }
}