package com.climbup.climbup.attempt.upload.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteMissionUploadSessionInitializeResponse {
    private String uploadId;
}
