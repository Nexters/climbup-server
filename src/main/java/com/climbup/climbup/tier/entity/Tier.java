package com.climbup.climbup.tier.entity;

import com.climbup.climbup.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tiers")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageUrl; // todo: 추후 상황별로 티어 이미지가 여러개 필요해질수도 있음

    @Column(name = "sr_min", nullable = false)
    private Integer srMin;

    @Column(name = "sr_max")
    private Integer srMax;

    public Tier(String name, Integer srMin, Integer srMax) {
        this.name = name;
        this.srMin = srMin;
        this.srMax = srMax;
    }
}