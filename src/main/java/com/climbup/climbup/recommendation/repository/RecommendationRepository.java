package com.climbup.climbup.recommendation.repository;

import com.climbup.climbup.recommendation.entity.ChallengeRecommendation;
import com.climbup.climbup.session.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<ChallengeRecommendation, Long> {
    List<ChallengeRecommendation> findBySession(UserSession session);
}
