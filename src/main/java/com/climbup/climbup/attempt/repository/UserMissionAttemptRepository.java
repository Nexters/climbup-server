package com.climbup.climbup.attempt.repository;

import com.climbup.climbup.attempt.dto.response.SessionAttemptDetail;
import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import com.climbup.climbup.attempt.upload.enums.UploadStatus;
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

    @Query("SELECT uma FROM UserMissionAttempt uma " +
            "LEFT JOIN uma.upload us " +
            "WHERE uma.user.id = :userId " +
            "AND (uma.upload IS NULL OR us.status IN (:notStarted, :inProgress, :failed)) " +
            "ORDER BY uma.createdAt DESC")
    List<UserMissionAttempt> findIncompleteAttemptsByUserId(
            @Param("userId") Long userId,
            @Param("notStarted") UploadStatus notStarted,
            @Param("inProgress") UploadStatus inProgress,
            @Param("failed") UploadStatus failed);

    @Query("SELECT new com.climbup.climbup.attempt.dto.response.SessionAttemptDetail(" +
            "uma.success, " +
            "m.difficulty, " +
            "gl.imageUrl, " +
            "m.removedAt, " +
            "m.thumbnailUrl, " +
            "m.videoUrl, " +
            "s.name, " +
            "m.score, " +
            "uma.thumbnailUrl, " +
            "uma.videoUrl) " +
            "FROM UserMissionAttempt uma " +
            "JOIN uma.mission m " +
            "JOIN m.sector s " +
            "JOIN m.gym g " +
            "JOIN g.brand b " +
            "JOIN GymLevel gl ON gl.brand = b AND gl.name = m.difficulty " +
            "WHERE uma.user.id = :userId " +
            "AND uma.session.id = :sessionId " +
            "ORDER BY uma.createdAt ASC")
    List<SessionAttemptDetail> findSessionAttemptsWithGymLevel(
            @Param("userId") Long userId,
            @Param("sessionId") Long sessionId);
}