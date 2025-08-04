package com.climbup.climbup.attempt.upload.dto.response;

import com.climbup.climbup.attempt.upload.entity.UploadSession;
import com.climbup.climbup.attempt.upload.enums.UploadStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@Schema(description = "영상 업로드 상태 응답")
public class RouteMissionUploadStatusResponse {

    @Schema(description = "업로드 상태", example = "in_progress")
    private UploadStatus status;

    @Schema(description = "업로드 ID", example = "8ded5806-87df-43b5-9c64-e4513eb33987")
    private UUID uploadId;

    @Schema(description = "생성 시각", example = "2025-07-31T14:20:00")
    private LocalDateTime createdAt;

    @Schema(description = "청크 목록")
    private UploadStatusChunkResponse chunks;

    public static RouteMissionUploadStatusResponse toDto(UploadSession uploadSession){
        return RouteMissionUploadStatusResponse.builder().status(uploadSession.getStatus()).uploadId(uploadSession.getId()).createdAt(uploadSession.getCreatedAt()).chunks(UploadStatusChunkResponse.toDto(uploadSession)).build();
    }
}
