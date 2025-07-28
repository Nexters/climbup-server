package com.climbup.climbup.level.dto.response;

import com.climbup.climbup.level.entity.Level;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LevelResponse {

    @Schema(description = "레벨 ID", example = "1")
    private Long id;

    @Schema(description = "레벨 이름", example = "V0")
    private String name;

    @Schema(description = "레벨 설명", example = "입문자용 가장 쉬운 난이도")
    private String description;

    @Schema(description = "정렬 순서", example = "1")
    private Integer sortOrder;

    public static LevelResponse fromEntity(Level level) {
        return LevelResponse.builder()
                .id(level.getId())
                .name(level.getName())
                .description(level.getDescription())
                .sortOrder(level.getSortOrder())
                .build();
    }
}
