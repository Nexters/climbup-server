package com.climbup.climbup.attempt.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteMissionUploadSessionFinalizeResponse {
    private String fileName;
}
