package com.climbup.climbup.attempt.service;

import com.climbup.climbup.attempt.dto.request.CreateAttemptRequest;
import com.climbup.climbup.attempt.dto.response.AttemptStatusResponse;
import com.climbup.climbup.attempt.dto.response.CreateAttemptResponse;
import com.climbup.climbup.attempt.dto.response.SessionAttemptResponse;
import com.climbup.climbup.attempt.dto.response.UserMissionAttemptResponse;
import com.climbup.climbup.attempt.upload.dto.request.RouteMissionUploadChunkRequest;
import com.climbup.climbup.attempt.upload.dto.request.RouteMissionUploadSessionInitializeRequest;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadChunkResponse;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadSessionFinalizeResponse;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadSessionInitializeResponse;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadStatusResponse;
import com.climbup.climbup.attempt.upload.enums.UploadStatus;
import com.climbup.climbup.attempt.dto.response.AttemptResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AttemptService {
    CreateAttemptResponse createAttempt(Long userId, CreateAttemptRequest request);

    RouteMissionUploadStatusResponse getAttemptUploadStatus(Long attemptId);

    RouteMissionUploadSessionInitializeResponse initializeAttemptUploadSession(Long attemptId, RouteMissionUploadSessionInitializeRequest request);

    void updateUploadSessionStatus(UUID uploadId, UploadStatus status);

    RouteMissionUploadChunkResponse uploadChunk(UUID uploadId, RouteMissionUploadChunkRequest request);

    RouteMissionUploadSessionFinalizeResponse finalizeUploadSession(UUID uploadId, MultipartFile thumbnailFile);

    SessionAttemptResponse getSessionAttempts(Long userId, Long sessionId);

    Page<AttemptResponse> getAttempts(Long userId, Long gymId, Boolean success, Pageable pageable);

    AttemptStatusResponse getAttemptStatus(Long attemptId);

    List<UserMissionAttemptResponse> getIncompleteAttempts(Long userId);
}
