package com.climbup.climbup.attempt.service;

import com.climbup.climbup.attempt.dto.request.CreateAttemptRequest;
import com.climbup.climbup.attempt.dto.response.CreateAttemptResponse;
import com.climbup.climbup.attempt.upload.dto.request.RouteMissionUploadChunkRequest;
import com.climbup.climbup.attempt.upload.dto.request.RouteMissionUploadSessionInitializeRequest;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadChunkResponse;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadSessionFinalizeResponse;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadSessionInitializeResponse;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadStatusResponse;

import java.util.UUID;

public interface AttemptService {
    CreateAttemptResponse createAttempt(Long userId, CreateAttemptRequest request);

    RouteMissionUploadStatusResponse getAttemptUploadStatus(Long attemptId);

    RouteMissionUploadSessionInitializeResponse initializeAttemptUploadSession(Long attemptId, RouteMissionUploadSessionInitializeRequest request);

    RouteMissionUploadChunkResponse uploadChunk(UUID uploadId, RouteMissionUploadChunkRequest request);

    RouteMissionUploadSessionFinalizeResponse finalizeUploadSession(UUID uploadId);
}
