package com.climbup.climbup.attempt.upload.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RouteMissionUploadSessionInitializeRequest {
    @Positive(message = "총 청크 길이는 0보다 커야합니다.")
    private Integer chunkLength;

    @Positive(message = "총 청크 크기는 0보다 커야합니다.")
    private Integer chunkSize;

    @Positive(message = "총 파일 크기는 0보다 커야합니다.")
    private Integer fileSize;

    @NotNull(message = "파일 이름은 필수입니다.")
    private String fileName;

    @NotNull(message = "파일 확장자는 필수입니다.")
    private String fileType;
}
