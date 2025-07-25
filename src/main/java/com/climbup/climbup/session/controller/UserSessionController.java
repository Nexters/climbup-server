package com.climbup.climbup.session.controller;

import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.session.dto.response.UserSessionResponses;
import com.climbup.climbup.session.entity.UserSession;
import com.climbup.climbup.session.repository.UserSessionRepository;
import com.climbup.climbup.session.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-sessions")
@RequiredArgsConstructor
public class UserSessionController {
    private final UserSessionService userSessionService;

    @PostMapping
    public ResponseEntity<ApiResult<UserSessionResponses.CreateUserSession>> startUserSession() {
        Long userId = 1L;
        
        UserSession session = userSessionService.startSession(userId);
        UserSessionResponses.CreateUserSession response = UserSessionResponses.CreateUserSession.toDto(session);
        
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResult<UserSessionResponses.FinishUserSession>> endUserSession(
            @PathVariable(name = "id") Long sessionId
    ){
        Long userId = 1L;

        UserSession session = userSessionService.finishSession(userId, sessionId);
        UserSessionResponses.FinishUserSession response = UserSessionResponses.FinishUserSession.toDto(session);
        
        return ResponseEntity.ok(ApiResult.success(response));
    }
}
