package com.climbup.climbup.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetUserLevelRequest {

    @NotNull
    @Schema(name = "levelId", description = "레벨 id")
    private Long levelId;
}
