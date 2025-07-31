package com.climbup.climbup.attempt.upload.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UploadStatusChunkResponse {
    private Integer totalReceived;

    private Integer totalExpected;

    private List<Integer> completedChunks;
}
