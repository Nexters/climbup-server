package com.climbup.climbup.common.exception;

import com.climbup.climbup.auth.exception.AuthHeaderMissingException;
import com.climbup.climbup.auth.exception.InvalidTokenException;
import com.climbup.climbup.auth.exception.TokenExpiredException;
import com.climbup.climbup.common.dto.ErrorResponse;
import com.climbup.climbup.global.discord.DiscordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final DiscordService discordService;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {

        ErrorCode errorCode = ex.getErrorCode();
        log.warn("Business exception occurred: {} - {}", errorCode.getCode(), ex.getMessage());

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode, request.getRequestURI(), ex.getMessageArgs()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        log.warn("Validation error occurred: {}", ex.getMessage());

        Map<String, Object> details = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                details.put(error.getField(), error.getDefaultMessage())
        );

        FieldError firstError = ex.getBindingResult().getFieldErrors().get(0);
        String errorMessage = firstError.getDefaultMessage();

        ErrorResponse response = ErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_ERROR.getCode())
                .message(ErrorCode.VALIDATION_ERROR.getMessage(errorMessage))
                .timestamp(java.time.LocalDateTime.now())
                .path(request.getRequestURI())
                .details(details)
                .build();

        return ResponseEntity
                .status(ErrorCode.VALIDATION_ERROR.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        log.warn("Malformed JSON request: {}", ex.getMessage());
        return ResponseEntity
                .status(ErrorCode.MALFORMED_JSON.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.MALFORMED_JSON, request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        log.warn("Parameter type mismatch: {}", ex.getMessage());
        return ResponseEntity
                .status(ErrorCode.PARAM_TYPE_MISMATCH.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.PARAM_TYPE_MISMATCH, request.getRequestURI(), ex.getName()));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(
            MissingRequestHeaderException ex, HttpServletRequest request) {

        log.warn("Missing required header: {}", ex.getHeaderName());
        return ResponseEntity
                .status(ErrorCode.AUTH_HEADER_MISSING.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.AUTH_HEADER_MISSING, request.getRequestURI(), ex.getHeaderName()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest request) {

        log.warn("Access denied: {}", ex.getMessage());
        return ResponseEntity
                .status(ErrorCode.ACCESS_DENIED.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.ACCESS_DENIED, request.getRequestURI()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpServletRequest request) {

        log.warn("No handler found for: {} {}", ex.getHttpMethod(), ex.getRequestURL());
        return ResponseEntity
                .status(ErrorCode.RESOURCE_NOT_FOUND.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.RESOURCE_NOT_FOUND, request.getRequestURI()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

        log.warn("Method not supported: {}", ex.getMethod());
        return ResponseEntity
                .status(ErrorCode.METHOD_NOT_SUPPORTED.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.METHOD_NOT_SUPPORTED, request.getRequestURI(), ex.getMethod()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, HttpServletRequest request) {

        log.warn("Illegal argument: {}", ex.getMessage());
        return ResponseEntity
                .status(ErrorCode.ILLEGAL_ARGUMENT.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.ILLEGAL_ARGUMENT, request.getRequestURI(), ex.getMessage()));
    }

    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
        public ResponseEntity<ErrorResponse> handleNoResourceFoundException(
                org.springframework.web.servlet.resource.NoResourceFoundException ex, 
                HttpServletRequest request) {

        log.debug("Failed Static Resource Request: {} {}", 
                request.getMethod(), request.getRequestURI());
        
        return ResponseEntity
                .status(ErrorCode.RESOURCE_NOT_FOUND.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.RESOURCE_NOT_FOUND, request.getRequestURI()));
        }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {

        log.error("Unexpected exception occurred", ex);
        sendDiscordErrorNotification(ex, "handleGenericException");
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, request.getRequestURI()));
    }

    @ExceptionHandler({InvalidTokenException.class, TokenExpiredException.class})
    public ResponseEntity<ErrorResponse> handleJwtException(
            BusinessException ex, HttpServletRequest request) {

        ErrorCode errorCode = ex.getErrorCode();
        log.warn("JWT 관련 예외 발생: {} - {}", errorCode.getCode(), ex.getMessage());

        Map<String, Object> details = new HashMap<>();

        if (ex instanceof TokenExpiredException) {
            details.put("shouldRefresh", true);
            details.put("action", "REFRESH_TOKEN");
        } else if (ex instanceof InvalidTokenException) {
            details.put("shouldRefresh", false);
            details.put("action", "REDIRECT_TO_LOGIN");
        }

        ErrorResponse response = ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage(ex.getMessageArgs()))
                .timestamp(java.time.LocalDateTime.now())
                .path(request.getRequestURI())
                .details(details)
                .build();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(AuthHeaderMissingException.class)
    public ResponseEntity<ErrorResponse> handleAuthHeaderMissingException(
            AuthHeaderMissingException ex, HttpServletRequest request) {

        log.warn("Authorization 헤더 누락: {}", ex.getMessage());

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ErrorResponse.of(ex.getErrorCode(), request.getRequestURI(), ex.getMessageArgs()));
    }

    private void sendDiscordErrorNotification(Exception ex, String methodName) {
        if (discordService == null) {
            return;
        }

        try {
            String stackTrace = getStackTrace(ex);
            String className = ex.getStackTrace().length > 0 ?
                    ex.getStackTrace()[0].getClassName() : "Unknown";
            String actualMethodName = ex.getStackTrace().length > 0 ?
                    ex.getStackTrace()[0].getMethodName() : methodName;

            discordService.sendErrorNotification(
                    ex.getMessage() + "\n\n" + stackTrace,
                    className,
                    actualMethodName
            );

        } catch (Exception discordEx) {
            log.error("Discord 알림 전송 중 오류 발생", discordEx);
        }
    }

    private String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);

        String fullStackTrace = sw.toString();
        if (fullStackTrace.length() > 1500) {
            return fullStackTrace.substring(0, 1500) + "\n... (truncated)";
        }
        return fullStackTrace;
    }
}