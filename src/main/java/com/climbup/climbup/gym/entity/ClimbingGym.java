package com.climbup.climbup.gym.entity;

import com.climbup.climbup.brand.entity.Brand;
import com.climbup.climbup.common.entity.BaseEntity;
import com.climbup.climbup.route.entity.RouteMission;
import com.climbup.climbup.sector.entity.Sector;
import com.climbup.climbup.session.entity.UserSession;
import com.climbup.climbup.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "climbing_gyms")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ClimbingGym extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(name = "branch_name", nullable = false, length = 100)
    private String branchName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "sector_info", columnDefinition = "TEXT")
    private String sectorInfo;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private List<RouteMission> routeMissions = new ArrayList<>();

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private List<UserSession> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public String getName() {
        return brand.getName() + " " + branchName;
    }
}