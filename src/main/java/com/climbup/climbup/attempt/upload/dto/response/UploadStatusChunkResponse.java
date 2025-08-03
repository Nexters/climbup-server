package com.climbup.climbup.attempt.upload.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "영상 청크별 업로드 상태 응답")
public class UploadStatusChunkResponse {

    @Schema(description = "현재까지 업로드된 갯수", example = "505050")
    private Integer totalReceived;

    @Schema(description = "총 업로드될 갯수", example = "10101010")
    private Integer totalExpected;

    @Schema(description = "현재까지 업로드된 청크 ID 목록", example = "[1, 2, 3, 4, 5, 6, 7, 10]")
    private List<Integer> completedChunks;
}
