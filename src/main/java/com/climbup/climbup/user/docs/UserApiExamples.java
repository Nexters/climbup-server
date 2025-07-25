package com.climbup.climbup.user.docs;

public class UserApiExamples {

    public static final String NEW_USER = """
            {
                "id": 1,
                "nickname": "클라이머이예림",
                "imageUrl": "https://k.kakaocdn.net/dn/profile.jpg",
                "sr": 600,
                "onboardingCompleted": false,
                "level": null,
                "gym": null
            }
            """;

    public static final String GYM_SELECTED = """
            {
                "id": 1,
                "nickname": "클라이머이예림",
                "imageUrl": "https://k.kakaocdn.net/dn/profile.jpg",
                "sr": 600,
                "onboardingCompleted": false,
                "level": null,
                "gym": {
                    "id": 1,
                    "name": "더클라임 강남점",
                    "location": "서울시 강남구",
                    "imageUrl": "https://example.com/gym1.jpg"
                }
            }
            """;

    public static final String LEVEL_SELECTED = """
            {
                "id": 1,
                "nickname": "클라이머이예림",
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
            """;

    public static final String COMPLETED_USER = """
            {
                "id": 1,
                "nickname": "클라이머이예림",
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
                    "name": "더클라임 강남점",
                    "location": "서울시 강남구",
                    "imageUrl": "https://example.com/gym1.jpg"
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