package com.climbup.climbup.attempt.service;

import com.climbup.climbup.attempt.dto.request.CreateAttemptRequest;
import com.climbup.climbup.attempt.dto.response.CreateAttemptResponse;

public interface AttemptService {

    CreateAttemptResponse createAttempt(Long userId, CreateAttemptRequest request);
}
