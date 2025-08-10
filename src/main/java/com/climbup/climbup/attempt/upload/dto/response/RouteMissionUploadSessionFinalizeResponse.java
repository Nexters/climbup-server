package com.climbup.climbup.attempt.upload.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "루트미션 업로드 세션 완료 응답")
public class RouteMissionUploadSessionFinalizeResponse {

    @Schema(description = "업로드된 파일명", example = "video_123456789.mp4")
    private String fileName;

    @Schema(description = "업로드된 영상 URL", example = "https://storage.com/videos/attempts/...")
    private String videoUrl;

    @Schema(description = "업로드된 썸네일 URL", example = "https://storage.com/images/attempts/...")
    private String thumbnailUrl;

    @Schema(description = "썸네일 업로드 여부", example = "true")
    private Boolean thumbnailUploaded;
}