package com.climbup.climbup.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetUserTierRequest {

    @NotNull
    @Schema(name = "tierId", description = "티어 id")
    private Long tierId;
}
