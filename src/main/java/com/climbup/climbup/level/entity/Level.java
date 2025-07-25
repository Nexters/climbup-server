package com.climbup.climbup.level.entity;

import com.climbup.climbup.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "levels")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Level extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;    // ORANGE, GREEN, BLUE, RED, PURPLE

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageUrl; // todo: 추후 상황별로 레벨 이미지가 여러개 필요해질수도 있음

    @Column(name = "sr_min", nullable = false)
    private Integer srMin;

    @Column(name = "sr_max")
    private Integer srMax;
}
