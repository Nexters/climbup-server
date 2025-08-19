package com.climbup.climbup.recommendation.repository;

import com.climbup.climbup.recommendation.entity.ChallengeRecommendation;
import com.climbup.climbup.session.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<ChallengeRecommendation, Long> {
    @Query("SELECT DISTINCT cr FROM ChallengeRecommendation cr " +
            "JOIN FETCH cr.mission rm " +
            "LEFT JOIN FETCH rm.attempts " +
            "JOIN FETCH rm.sector " +
            "WHERE cr.session = :session " +
            "ORDER BY cr.recommendedOrder")
    List<ChallengeRecommendation> findBySession(@Param("session") UserSession session);

}
