package com.climbup.climbup.attempt.repository;

import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserMissionAttemptRepository extends JpaRepository<UserMissionAttempt, Long> {

    @Query("SELECT uma FROM UserMissionAttempt uma WHERE uma.user.id = :userId AND uma.session.id = :sessionId")
    List<UserMissionAttempt> findByUserIdAndSessionId(@Param("userId") Long userId, @Param("sessionId") Long sessionId);

    @Query("SELECT uma FROM UserMissionAttempt uma WHERE uma.upload.id = :uploadId")
    Optional<UserMissionAttempt> findByUploadId(@Param("uploadId") UUID uploadId);
}