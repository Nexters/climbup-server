package com.climbup.climbup.user.docs;

public class UserApiExamples {
    public static final String UNAUTHORIZED_ERROR = """
            {
                "errorCode": "AUTH_001",
                "message": "필수 헤더가 누락되었습니다: Authorization",
                "timestamp": "2024-01-15T10:30:00",
                "path": "/api/users/me"
            }
            """;

    public static final String NOT_FOUND_ERROR = """
            {
                "errorCode": "RESOURCE_001",
                "message": "요청한 리소스를 찾을 수 없습니다.",
                "timestamp": "2024-01-15T10:30:00",
                "path": "/api/users/me"
            }
            """;
}