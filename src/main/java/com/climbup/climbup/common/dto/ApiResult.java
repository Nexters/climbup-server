package com.climbup.climbup.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Api 응답 정보")
public class ApiResult<T> {

    @Schema(description = "성공 여부", example = "true")
    private Boolean success;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    @Schema(description = "에러 코드", example = "AUTH_001")
    private String errorCode;

    public static <T> ApiResult<T> success(T data) {
        return ApiResult.<T>builder()
                .success(true)
                .message("요청이 성공적으로 처리되었습니다.")
                .data(data)
                .build();
    }

    public static <T> ApiResult<T> success(String message, T data) {
        return ApiResult.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResult<Void> success() {
        return ApiResult.<Void>builder()
                .success(true)
                .message("요청이 성공적으로 처리되었습니다.")
                .build();
    }

    public static ApiResult<Void> success(String message) {
        return ApiResult.<Void>builder()
                .success(true)
                .message(message)
                .build();
    }

    public static <T> ApiResult<T> error(String errorCode, String message) {
        return ApiResult.<T>builder()
                .success(false)
                .errorCode(errorCode)
                .message(message)
                .build();
    }
}