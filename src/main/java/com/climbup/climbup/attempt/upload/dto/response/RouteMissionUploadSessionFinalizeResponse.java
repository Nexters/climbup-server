package com.climbup.climbup.attempt.upload.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "영상 업로드 세션 완료 응답")
public class RouteMissionUploadSessionFinalizeResponse {

    @Schema(description = "파일명", example = "thisIsFileName")
    private String fileName;
}
