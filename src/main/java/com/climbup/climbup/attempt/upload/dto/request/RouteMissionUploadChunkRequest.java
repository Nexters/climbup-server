package com.climbup.climbup.attempt.upload.dto.request;

import lombok.Data;

@Data
public class RouteMissionUploadChunkRequest {

    private Integer index;

    private byte[] chunk;
}