package com.climbup.climbup.attempt.upload.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RouteMissionUploadChunkRequest {

    @PositiveOrZero(message = "청크 인덱스는 0보다 커야합니다.")
    private Integer index;

    @Size(max = 50_000_000, message = "개별 청크 크기는 50MB를 초과하면 안됩니다.")
    private byte[] chunk;
}