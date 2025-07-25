package com.climbup.climbup.session.controller;

import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.session.dto.response.UserSessionResponses;
import com.climbup.climbup.session.entity.UserSession;
import com.climbup.climbup.session.repository.UserSessionRepository;
import com.climbup.climbup.session.service.UserSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-sessions")
@Tag(name = "User Session", description = "오늘의 세션 관련 API")
@RequiredArgsConstructor
public class UserSessionController {
    private final UserSessionService userSessionService;

    @Operation(summary = "오늘의 세션 시작하기", description = "유저의 오늘의 세션을 시작", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 오늘의 세션을 시작"),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 진행중인 오늘의 세션이 존재",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "errorCode": "SESSION_003",
                                                "message": "아직 마무리되지 않은 세션이 존재합니다.",
                                                "timestamp": "2025-07-25T10:30:00",
                                                "path": "/api/user-sessions"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ApiResult<UserSessionResponses.CreateUserSession>> startUserSession() {
        Long userId = 1L;
        
        UserSession session = userSessionService.startSession(userId);
        UserSessionResponses.CreateUserSession response = UserSessionResponses.CreateUserSession.toDto(session);
        
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @Operation(summary = "오늘의 세션 종료하기", description = "유저의 오늘의 세션을 종료", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 오늘의 세션을 종료"),
            @ApiResponse(
                    responseCode = "404",
                    description = "진행중인 오늘의 세션이 없는 경우",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "errorCode": "SESSION_001",
                                                "message": "세션이 존재하지 않습니다.",
                                                "timestamp": "2025-07-25T10:30:00",
                                                "path": "/api/user-sessions/{id}"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "유저가 이미 오늘의 세션을 종료한 경우",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "errorCode": "SESSION_002",
                                                "message": "이미 종료된 세션입니다.",
                                                "timestamp": "2025-07-25T10:30:00",
                                                "path": "/api/user-sessions/{id}"
                                            }
                                            """
                            )
                    )
            )
    })
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
