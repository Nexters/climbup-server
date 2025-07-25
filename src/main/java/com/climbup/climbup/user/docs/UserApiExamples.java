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
                "timestamp": "2024-01-15T10:30:00.000+00:00",
                "status": 401,
                "error": "Unauthorized",
                "message": "JWT 토큰이 유효하지 않습니다",
                "path": "/api/users/me"
            }
            """;

    public static final String NOT_FOUND_ERROR = """
            {
                "timestamp": "2024-01-15T10:30:00.000+00:00",
                "status": 404,
                "error": "Not Found",
                "message": "사용자를 찾을 수 없습니다",
                "path": "/api/users/me"
            }
            """;
}