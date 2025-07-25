package com.climbup.climbup.session.repository;

import com.climbup.climbup.session.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
}
