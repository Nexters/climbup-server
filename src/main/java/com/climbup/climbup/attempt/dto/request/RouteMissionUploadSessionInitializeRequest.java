package com.climbup.climbup.attempt.dto.request;

import lombok.Data;

@Data
public class RouteMissionUploadSessionInitializeRequest {
    private Integer chunkLength;

    private Integer chunkSize;

    private Integer fileSize;

    private String fileName;

    private String fileType;
}
