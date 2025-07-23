-- 티어 테이블
CREATE TABLE tiers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    image_url TEXT NOT NULL,
    sr_min INTEGER NOT NULL,
    sr_max INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 클라이밍 짐 테이블
CREATE TABLE climbing_gyms (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    sector_info TEXT,
    image_url TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 섹터 테이블
CREATE TABLE sectors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    image_url TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 사용자 테이블
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    kakao_id VARCHAR(50) NOT NULL UNIQUE,
    nickname VARCHAR(50) NOT NULL UNIQUE,
    sr INTEGER NOT NULL DEFAULT 600,
    tier_id BIGINT NOT NULL REFERENCES tiers(id),
    image_url TEXT NOT NULL,
    onboarding_completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 루트 미션 테이블
CREATE TABLE route_missions (
    id BIGSERIAL PRIMARY KEY,
    gym_id BIGINT NOT NULL REFERENCES climbing_gyms(id),
    sector_id BIGINT NOT NULL REFERENCES sectors(id),
    difficulty VARCHAR(10) NOT NULL,
    score INTEGER NOT NULL,
    image_url TEXT NOT NULL,
    video_url TEXT NOT NULL,
    posted_at TIMESTAMP NOT NULL,
    removed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 사용자 세션 테이블
CREATE TABLE user_sessions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    session_date DATE NOT NULL,
    started_at TIMESTAMP NOT NULL,
    ended_at TIMESTAMP,
    total_duration INTEGER,
    sr_gained INTEGER NOT NULL DEFAULT 0,
    completed_count INTEGER NOT NULL DEFAULT 0,
    attempted_count INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 사용자 미션 시도 테이블
CREATE TABLE user_mission_attempts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    session_id BIGINT NOT NULL REFERENCES user_sessions(id),
    mission_id BIGINT NOT NULL REFERENCES route_missions(id),
    success BOOLEAN NOT NULL,
    video_url TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 추천 테이블
CREATE TABLE challenge_recommendations (
    id BIGSERIAL PRIMARY KEY,
    session_id BIGINT NOT NULL REFERENCES user_sessions(id),
    mission_id BIGINT NOT NULL REFERENCES route_missions(id),
    recommended_order INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- SR 히스토리 테이블
CREATE TABLE sr_histories (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    session_id BIGINT NOT NULL REFERENCES user_sessions(id),
    mission_id BIGINT NOT NULL REFERENCES route_missions(id),
    sr_before INTEGER,
    sr_after INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 인덱스 생성
CREATE INDEX idx_session_user_date ON user_sessions(user_id, session_date);
CREATE INDEX idx_attempt_user_session ON user_mission_attempts(user_id, session_id);