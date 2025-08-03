package com.climbup.climbup.attempt.upload.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "루트미션 시도 영상 업로드 세션 생성")
public class RouteMissionUploadSessionInitializeRequest {

    @Positive(message = "총 청크 길이는 0보다 커야합니다.")
    @Schema(description = "총 청크 개수", example = "20")
    private Integer chunkLength;

    @Positive(message = "총 청크 크기는 0보다 커야합니다.")
    @Schema(description = "총 청크 크기", example = "5000000")
    private Long chunkSize;

    @Positive(message = "총 파일 크기는 0보다 커야합니다.")
    @Schema(description = "총 파일 크기", example = "5151515")
    private Long fileSize;

    @NotNull(message = "파일 이름은 필수입니다.")
    @Schema(description = "파일 이름", example = "orange_12_attempt")
    private String fileName;

    @NotNull(message = "파일 확장자는 필수입니다.")
    @Schema(description = "파일 확장자", example = "mp4")
    private String fileType;
}
