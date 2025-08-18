package com.climbup.climbup.recommendation.repository;

import com.climbup.climbup.recommendation.entity.ChallengeRecommendation;
import com.climbup.climbup.session.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<ChallengeRecommendation, Long> {
    @Query("""
        SELECT DISTINCT cr
        FROM ChallengeRecommendation cr
        JOIN FETCH cr.mission rm
        JOIN FETCH rm.sector s
        WHERE cr.session = :session
          AND NOT EXISTS (
              SELECT 1
              FROM UserMissionAttempt a
              WHERE a.mission = rm
                AND a.user = cr.session.user
                AND a.success = TRUE
          )
        ORDER BY cr.recommendedOrder
    """)
    List<ChallengeRecommendation> findNotSolvedRecommendationsBySession(@Param("session") UserSession session);

}
