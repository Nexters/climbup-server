package com.climbup.climbup.level.entity;

import com.climbup.climbup.common.entity.BaseEntity;
import com.climbup.climbup.gym.entity.GymLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private List<GymLevel> gymLevels = new ArrayList<>();
}