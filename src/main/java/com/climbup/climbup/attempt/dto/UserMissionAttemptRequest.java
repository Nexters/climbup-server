package com.climbup.climbup.attempt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserMissionAttemptRequest {
    @NotBlank
    private Long missionId;

    @NotNull
    private Boolean success;
}
