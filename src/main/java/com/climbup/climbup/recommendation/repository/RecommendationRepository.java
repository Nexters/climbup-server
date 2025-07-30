package com.climbup.climbup.recommendation.repository;

import com.climbup.climbup.recommendation.entity.ChallengeRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<ChallengeRecommendation, Long> {
}
