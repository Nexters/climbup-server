package com.climbup.climbup.recommendation.entity;

import com.climbup.climbup.common.entity.BaseEntity;
import com.climbup.climbup.route.entity.RouteMission;
import com.climbup.climbup.session.entity.UserSession;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "challenge_recommendations")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ChallengeRecommendation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private UserSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private RouteMission mission;

    @Column(name = "recommended_order", nullable = false)
    private Integer recommendedOrder;

    public String getDifficulty() {
        return mission != null ? mission.getDifficulty() : null;
    }
}
