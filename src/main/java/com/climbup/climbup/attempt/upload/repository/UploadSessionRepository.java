package com.climbup.climbup.attempt.upload.repository;

import com.climbup.climbup.attempt.upload.entity.UploadSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UploadSessionRepository extends JpaRepository<UploadSession, UUID> {
}