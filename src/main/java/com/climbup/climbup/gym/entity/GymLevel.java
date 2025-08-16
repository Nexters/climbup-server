package com.climbup.climbup.gym.entity;

import com.climbup.climbup.brand.entity.Brand;
import com.climbup.climbup.common.converter.StringListConverter;
import com.climbup.climbup.common.entity.BaseEntity;
import com.climbup.climbup.level.entity.Level;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "gym_levels")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class GymLevel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "sr_min", nullable = false)
    private Integer srMin;

    @Column(name = "sr_max")
    private Integer srMax;

    @Convert(converter = StringListConverter.class)
    @Column(name = "image_urls", nullable = false, columnDefinition = "JSON")
    private List<String> imageUrls;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;
}