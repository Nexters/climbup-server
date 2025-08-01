package com.climbup.climbup.recommendation.service;

import com.climbup.climbup.recommendation.dto.response.RouteMissionRecommendationResponse;
import com.climbup.climbup.session.entity.UserSession;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RecommendationService {
    void generateRecommendationsForSession(UserSession session);
    List<RouteMissionRecommendationResponse> getRecommendationsByUserActiveSession(Long userId);
}
