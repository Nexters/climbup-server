package com.climbup.climbup.attempt.upload.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Schema(description = "영상 업로드 세션 생성 응답")
public class RouteMissionUploadSessionInitializeResponse {

    @Schema(description = "업로드 ID", example = "8ded5806-87df-43b5-9c64-e4513eb33987")
    private UUID uploadId;
}
