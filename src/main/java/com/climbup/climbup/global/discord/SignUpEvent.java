package com.climbup.climbup.global.discord;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SignUpEvent extends ApplicationEvent {

    private final String nickname;
    private final Long userId;

    public SignUpEvent(Object source, String nickname, Long userId) {
        super(source);
        this.nickname = nickname;
        this.userId = userId;
    }
}