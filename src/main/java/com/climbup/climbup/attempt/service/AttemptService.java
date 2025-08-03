package com.climbup.climbup.attempt.service;

import com.climbup.climbup.attempt.dto.request.CreateAttemptRequest;
import com.climbup.climbup.attempt.dto.response.CreateAttemptResponse;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadStatusResponse;

public interface AttemptService {
    CreateAttemptResponse createAttempt(Long userId, CreateAttemptRequest request);

    RouteMissionUploadStatusResponse getAttemptUploadStatus(Long attemptId);
}
