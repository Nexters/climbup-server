package com.climbup.climbup.attempt.upload.dto.response;

import com.climbup.climbup.attempt.upload.enums.UploadStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
public class RouteMissionUploadStatusResponse {
    private UploadStatus status;

    private String uploadId;

    private LocalDateTime createdAt;

    private UploadStatusChunkResponse chunks;
}
