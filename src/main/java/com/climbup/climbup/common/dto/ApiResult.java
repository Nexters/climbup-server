package com.climbup.climbup.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Api 응답 정보")
public class ApiResult<T> {

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    public static <T> ApiResult<T> success(T data) {
        return ApiResult.<T>builder()
                .message("요청이 성공적으로 처리되었습니다.")
                .data(data)
                .build();
    }

    public static <T> ApiResult<T> success(String message, T data) {
        return ApiResult.<T>builder()
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResult<Void> success() {
        return ApiResult.<Void>builder()
                .message("요청이 성공적으로 처리되었습니다.")
                .build();
    }

    public static ApiResult<Void> success(String message) {
        return ApiResult.<Void>builder()
                .message(message)
                .build();
    }

}
