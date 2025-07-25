package com.climbup.climbup.session.service;

import com.climbup.climbup.session.entity.UserSession;

public interface UserSessionService {
    UserSession startSession(Long userId);
    UserSession finishSession(Long userId, Long id);
}
