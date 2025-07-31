package com.climbup.climbup.attempt.dto.response;

import com.climbup.climbup.attempt.enums.UploadStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
public class RouteMissionUploadStatusResponse {
    private UploadStatus status;

    private Optional<String> uploadId;

    private LocalDateTime createdAt;

    private UploadStatusChunkResponse chunks;
}
