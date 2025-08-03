package com.climbup.climbup.attempt.upload.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "루트미션 도전 영상 업로드 응답")
public class RouteMissionUploadChunkResponse {

    @Schema(description = "인덱스 번호", example = "5")
    private Integer index;

    @Schema(description = "현재까지 업로드된 갯수", example = "5")
    private Integer totalChunkReceived;

    @Schema(description = "총 업로드될 갯수", example = "20")
    private Integer totalChunkExpected;
}
