package com.climbup.climbup.attempt.upload.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RouteMissionUploadSessionInitializeRequest {
    @Min(value = 0)
    private Integer chunkLength;

    @Min(value = 0)
    private Integer chunkSize;

    @Min(value = 0)
    private Integer fileSize;

    @NotNull
    private String fileName;

    @NotNull
    private String fileType;
}
