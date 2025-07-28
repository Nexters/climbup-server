package com.climbup.climbup.session.repository;

import com.climbup.climbup.session.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByUserIdAndEndedAtIsNull(Long userId);
}
