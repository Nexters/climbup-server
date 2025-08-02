package com.climbup.climbup.user.controller;

import com.climbup.climbup.auth.util.SecurityUtil;
import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.user.docs.UserApiDocs;
import com.climbup.climbup.user.docs.UserApiExamples;
import com.climbup.climbup.user.dto.response.UserStatusResponse;
import com.climbup.climbup.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 정보 관리 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = UserApiDocs.GET_USER_STATUS_SUMMARY,
            description = UserApiDocs.GET_USER_STATUS_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserStatusResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "신규 사용자",
                                            summary = "온보딩 미완료",
                                            value = UserApiExamples.NEW_USER
                                    ),
                                    @ExampleObject(
                                            name = "암장만 선택",
                                            summary = "암장 선택 완료, 레벨 선택 필요",
                                            value = UserApiExamples.GYM_SELECTED
                                    ),
                                    @ExampleObject(
                                            name = "레벨만 선택",
                                            summary = "레벨 선택 완료, 암장 선택 필요",
                                            value = UserApiExamples.LEVEL_SELECTED
                                    ),
                                    @ExampleObject(
                                            name = "온보딩 완료",
                                            summary = "모든 설정 완료",
                                            value = UserApiExamples.COMPLETED_USER
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(
                            examples = @ExampleObject(value = UserApiExamples.UNAUTHORIZED_ERROR)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 없음",
                    content = @Content(
                            examples = @ExampleObject(value = UserApiExamples.NOT_FOUND_ERROR)
                    )
            )
    })
    @GetMapping("/me")
    public ResponseEntity<ApiResult<UserStatusResponse>> getCurrentUserStatus() {
        Long userId = SecurityUtil.getCurrentUserId();

        UserStatusResponse userStatus = userService.getUserStatus(userId);
        return ResponseEntity.ok(ApiResult.success("사용자 상태가 성공적으로 조회되었습니다.", userStatus));
    }
}