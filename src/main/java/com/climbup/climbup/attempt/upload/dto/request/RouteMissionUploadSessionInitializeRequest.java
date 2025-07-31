package com.climbup.climbup.attempt.upload.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RouteMissionUploadSessionInitializeRequest {
    @Positive
    private Integer chunkLength;

    @Positive
    private Integer chunkSize;

    @Positive
    private Integer fileSize;

    @NotNull
    private String fileName;

    @NotNull
    private String fileType;
}
