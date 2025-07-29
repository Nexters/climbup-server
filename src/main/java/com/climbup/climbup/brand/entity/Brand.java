package com.climbup.climbup.brand.entity;

import com.climbup.climbup.common.entity.BaseEntity;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.gym.entity.GymLevel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Brand extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name; // "더클라임", "클라이밍파크" 등

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "logo_url", columnDefinition = "TEXT")
    private String logoUrl;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<ClimbingGym> gyms = new ArrayList<>();

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<GymLevel> gymLevels = new ArrayList<>();
}