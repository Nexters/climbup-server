package com.climbup.climbup.attempt.upload.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RouteMissionUploadChunkRequest {

    @Min(value = 0)
    private Integer index;

    @Size(max = 50_000_000)
    private byte[] chunk;
}