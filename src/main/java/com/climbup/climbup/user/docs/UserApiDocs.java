package com.climbup.climbup.user.docs;

public class UserApiDocs {

    public static final String GET_USER_STATUS_SUMMARY = "현재 사용자 상태 조회";

    public static final String GET_USER_STATUS_DESCRIPTION = """
            현재 로그인한 사용자의 정보와 온보딩 상태를 조회합니다.
            
            **응답 정보:**
            - 사용자 기본 정보 (닉네임, 이미지, SR)
            - 온보딩 완료 여부 및 필요한 설정 항목
            - 선택된 암장/레벨 정보
            """;

    public static final String AUTHORIZATION_DESCRIPTION = """
            JWT 토큰 형식: Bearer {token}
            카카오 로그인 완료 후 받은 토큰을 사용하세요.
            """;

    public static final String AUTHORIZATION_EXAMPLE = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5NTUyMDAwfQ.example";
}