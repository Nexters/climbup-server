package com.climbup.climbup.sector.entity;

import com.climbup.climbup.route.entity.RouteMission;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sectors")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "score", nullable = false)
    private Integer score; //todo: RouteMission에 score가 있는데 Sector에도 필요한가?

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL)
    private List<RouteMission> routeMissions = new ArrayList<>();

    public Sector(String name, Integer score, String imageUrl) {
        this.name = name;
        this.score = score;
        this.imageUrl = imageUrl;
    }
}