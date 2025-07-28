package com.climbup.climbup.route.entity;

import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import com.climbup.climbup.common.entity.BaseEntity;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.recommendation.entity.ChallengeRecommendation;
import com.climbup.climbup.sector.entity.Sector;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "route_missions")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class RouteMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id", nullable = false)
    private ClimbingGym gym;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    @Column(name = "difficulty", nullable = false, length = 10)
    private String difficulty;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "thumbnail_url", nullable = false, columnDefinition = "TEXT")
    private String thumbnailUrl;

    @Column(name = "video_url", nullable = false, columnDefinition = "TEXT")
    private String videoUrl;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<UserMissionAttempt> attempts = new ArrayList<>();

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<ChallengeRecommendation> recommendations = new ArrayList<>();

    @Column(name = "posted_at", nullable = false)
    private LocalDateTime postedAt;

    @Column(name = "removed_at")
    private LocalDateTime removedAt;
}