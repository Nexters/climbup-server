package com.climbup.climbup.recommendation.entity;

import com.climbup.climbup.route.entity.RouteMission;
import com.climbup.climbup.session.entity.UserSession;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "challenge_recommendations")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private UserSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private RouteMission mission;

    @Column(name = "recommended_order", nullable = false)
    private Integer recommendedOrder;

    @Column(name = "difficulty", nullable = false, length = 10)
    private String difficulty;

    public ChallengeRecommendation(UserSession session, RouteMission mission, Integer recommendedOrder, String difficulty) {
        this.session = session;
        this.mission = mission;
        this.recommendedOrder = recommendedOrder;
        this.difficulty = difficulty;
    }
}
