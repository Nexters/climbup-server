package com.climbup.climbup.gym.entity;

import com.climbup.climbup.common.entity.BaseEntity;
import com.climbup.climbup.route.entity.RouteMission;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "climbing_gyms")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClimbingGym extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "sector_info", columnDefinition = "TEXT")
    private String sectorInfo;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private List<RouteMission> routeMissions = new ArrayList<>();

    public ClimbingGym(String name, String location, String sectorInfo) {
        this.name = name;
        this.location = location;
        this.sectorInfo = sectorInfo;
    }
}