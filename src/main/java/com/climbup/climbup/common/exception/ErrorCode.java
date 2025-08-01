package com.climbup.climbup.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 공통 에러
    INTERNAL_SERVER_ERROR(ErrorCategory.COMMON, "001", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 인증/보안 관련
    AUTH_HEADER_MISSING(ErrorCategory.AUTH, "001", "필수 헤더가 누락되었습니다: {0}", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED(ErrorCategory.AUTH, "002", "접근이 거부되었습니다.", HttpStatus.FORBIDDEN),
    INVALID_TOKEN(ErrorCategory.AUTH, "003", "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(ErrorCategory.AUTH, "004", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    NICKNAME_GENERATE_ERROR(ErrorCategory.AUTH, "005", "닉네임 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 사용자 관련
    USER_NOT_FOUND(ErrorCategory.USER, "001", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_ONBOARDING_ALREADY_COMPLETE(ErrorCategory.USER, "002", "이미 온보딩을 완료한 사용자입니다.", HttpStatus.CONFLICT),
    USER_ALREADY_EXISTS(ErrorCategory.USER, "003", "이미 존재하는 사용자입니다.", HttpStatus.CONFLICT),

    // 세션 관련
    SESSION_NOT_FOUND(ErrorCategory.SESSION, "001", "세션을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SESSION_ALREADY_FINISHED(ErrorCategory.SESSION, "002", "이미 완료된 세션입니다.", HttpStatus.CONFLICT),
    SESSION_NOT_YET_FINISHED(ErrorCategory.SESSION, "003", "세션이 아직 진행중입니다.", HttpStatus.CONFLICT),

    // 암장 관련
    GYM_LEVEL_BRAND_MISMATCH(ErrorCategory.GYM, "001", "선택한 암장과 레벨의 브랜드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    GYM_NOT_SELECTED(ErrorCategory.GYM, "002", "암장이 선택되지 않았습니다.", HttpStatus.BAD_REQUEST),

    // 추천 관련
    RECOMMENDATION_NOT_FOUND(ErrorCategory.RECOMMENDATION, "001", "추천을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 검증 관련
    VALIDATION_ERROR(ErrorCategory.VALIDATION, "001", "입력값이 올바르지 않습니다: {0}", HttpStatus.BAD_REQUEST),
    REQUIRED_FIELD_MISSING(ErrorCategory.VALIDATION, "002", "필수 필드가 누락되었습니다: {0}", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT(ErrorCategory.VALIDATION, "003", "형식이 올바르지 않습니다: {0}", HttpStatus.BAD_REQUEST),
    INVALID_DIFFICULTY_LEVEL(ErrorCategory.VALIDATION, "004", "유효하지 않은 난이도입니다: {0}", HttpStatus.BAD_REQUEST),

    // 요청 관련
    MALFORMED_JSON(ErrorCategory.REQUEST, "001", "JSON 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    PARAM_TYPE_MISMATCH(ErrorCategory.REQUEST, "002", "요청 파라미터 타입이 올바르지 않습니다: {0}", HttpStatus.BAD_REQUEST),
    METHOD_NOT_SUPPORTED(ErrorCategory.REQUEST, "003", "지원하지 않는 HTTP 메서드입니다: {0}", HttpStatus.METHOD_NOT_ALLOWED),
    ILLEGAL_ARGUMENT(ErrorCategory.REQUEST, "004", "잘못된 인자가 전달되었습니다: {0}", HttpStatus.BAD_REQUEST),

    // 리소스 관련
    RESOURCE_NOT_FOUND(ErrorCategory.RESOURCE, "001", "요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    RESOURCE_ALREADY_EXISTS(ErrorCategory.RESOURCE, "002", "이미 존재하는 리소스입니다.", HttpStatus.CONFLICT),
    RESOURCE_ACCESS_DENIED(ErrorCategory.RESOURCE, "003", "리소스에 접근할 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // 비즈니스 로직 관련
    INVALID_BUSINESS_RULE(ErrorCategory.BUSINESS, "001", "비즈니스 규칙에 위배됩니다: {0}", HttpStatus.BAD_REQUEST),
    GYM_NOT_FOUND(ErrorCategory.BUSINESS, "002", "암장을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    LEVEL_NOT_FOUND(ErrorCategory.BUSINESS, "003", "레벨을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    GYM_LEVEL_NOT_FOUND(ErrorCategory.BUSINESS, "004", "암장 레벨을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    BRAND_NOT_FOUND(ErrorCategory.BUSINESS, "005", "암장 브랜드를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SECTOR_NOT_FOUND(ErrorCategory.BUSINESS, "006", "섹터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ROUTE_NOT_FOUND(ErrorCategory.BUSINESS, "007", "루트미션을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ATTEMPT_NOT_FOUND(ErrorCategory.BUSINESS, "008", "도전 기록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SR_HISTORY_NOT_FOUND(ErrorCategory.BUSINESS, "009", "SR 기록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final ErrorCategory category;
    private final String number;
    private final String messageTemplate;
    private final HttpStatus httpStatus;

    ErrorCode(ErrorCategory category, String number, String messageTemplate, HttpStatus httpStatus) {
        this.category = category;
        this.number = number;
        this.messageTemplate = messageTemplate;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return category.getPrefix() + "_" + number;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public String getMessage(Object... args) {
        if (args == null || args.length == 0) {
            return messageTemplate;
        }
        return formatMessage(messageTemplate, args);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ErrorCategory getCategory() {
        return category;
    }

    private String formatMessage(String template, Object... args) {
        String result = template;
        for (int i = 0; i < args.length; i++) {
            result = result.replace("{" + i + "}", String.valueOf(args[i]));
        }
        return result;
    }
}