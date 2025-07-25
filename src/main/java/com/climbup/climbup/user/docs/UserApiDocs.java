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
}