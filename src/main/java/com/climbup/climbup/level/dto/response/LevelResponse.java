package com.climbup.climbup.level.dto.response;

import com.climbup.climbup.level.entity.Level;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LevelResponse {

    @Schema(description = "레벨 ID", example = "2")
    private Long id;

    @Schema(description = "레벨 이름", example = "GREEN")
    private String name;

    @Schema(description = "레벨 이미지 URL", example = "https://example.com/green.png")
    private String imageUrl;

    public static LevelResponse fromEntity(Level level) {
        return LevelResponse.builder()
                .id(level.getId())
                .name(level.getName())
                .imageUrl(level.getImageUrl())
                .build();
    }
}
