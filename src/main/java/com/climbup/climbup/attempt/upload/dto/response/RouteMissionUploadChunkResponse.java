package com.climbup.climbup.attempt.upload.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteMissionUploadChunkResponse {
    private Integer index;
    private Integer totalChunkReceived;
    private Integer totalChunkExpected;
}
