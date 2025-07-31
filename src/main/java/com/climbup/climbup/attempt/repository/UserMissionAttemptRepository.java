package com.climbup.climbup.attempt.repository;

import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMissionAttemptRepository extends JpaRepository<UserMissionAttempt, Long> {

    @Query("SELECT uma FROM UserMissionAttempt uma WHERE uma.user.id = :userId AND uma.session.id = :sessionId")
    List<UserMissionAttempt> findByUserIdAndSessionId(@Param("userId") Long userId, @Param("sessionId") Long sessionId);
}