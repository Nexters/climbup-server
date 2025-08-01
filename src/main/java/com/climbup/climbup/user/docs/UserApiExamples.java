package com.climbup.climbup.user.docs;

public class UserApiExamples {

    public static final String NEW_USER = """
            {
                "message": "사용자 상태가 성공적으로 조회되었습니다.",
                "data": {
                    "id": 1,
                    "name": "이예림",
                    "nickname": "당당한카피바라",
                    "imageUrl": "https://k.kakaocdn.net/dn/profile.jpg",
                    "sr": 600,
                    "onboardingCompleted": false,
                    "level": null,
                    "gym": null
                }
            }
            """;

    public static final String GYM_SELECTED = """
            {
                "message": "사용자 상태가 성공적으로 조회되었습니다.",
                "data": {
                    "id": 1,
                    "name": "이예림",
                    "nickname": "당당한카피바라",
                    "imageUrl": "https://k.kakaocdn.net/dn/profile.jpg",
                    "sr": 600,
                    "onboardingCompleted": false,
                    "level": null,
                    "gym": {
                        "id": 1,
                        "name": "더클라임",
                        "location": "강남점",
                        "address": "서울특별시 강남구 테헤란로8길 21 지하 1층",
                        "imageUrl": "https://example.com/gym1.jpg"
                    }
                }
            }
            """;

    public static final String LEVEL_SELECTED = """
            {
                "message": "사용자 상태가 성공적으로 조회되었습니다.",
                "data": {
                    "id": 1,
                    "name": "이예림",
                    "nickname": "당당한카피바라",
                    "imageUrl": "https://k.kakaocdn.net/dn/profile.jpg",
                    "sr": 600,
                    "onboardingCompleted": false,
                    "level": {
                        "id": 2,
                        "name": "GREEN",
                        "imageUrl": "https://example.com/green.png",
                        "srMin": 650,
                        "srMax": 999
                    },
                    "gym": null
                }
            }
            """;

    public static final String COMPLETED_USER = """
            {
                "message": "사용자 상태가 성공적으로 조회되었습니다.",
                "data": {
                    "id": 1,
                    "name": "이예림",
                    "nickname": "당당한카피바라",
                    "imageUrl": "https://k.kakaocdn.net/dn/profile.jpg",
                    "sr": 600,
                    "onboardingCompleted": true,
                    "level": {
                        "id": 2,
                        "name": "GREEN",
                        "imageUrl": "https://example.com/green.png",
                        "srMin": 650,
                        "srMax": 999
                    },
                    "gym": {
                        "id": 1,
                        "name": "더클라임",
                        "location": "강남점",
                        "address": "서울특별시 강남구 테헤란로8길 21 지하 1층",
                        "imageUrl": "https://example.com/gym1.jpg"
                    }
                }
            }
            """;

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