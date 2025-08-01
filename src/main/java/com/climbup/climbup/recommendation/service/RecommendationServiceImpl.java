package com.climbup.climbup.recommendation.service;

import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.recommendation.dto.response.RouteMissionRecommendationResponse;
import com.climbup.climbup.recommendation.entity.ChallengeRecommendation;
import com.climbup.climbup.recommendation.repository.RecommendationRepository;
import com.climbup.climbup.route.entity.RouteMission;
import com.climbup.climbup.route.repository.RouteMissionRepository;
import com.climbup.climbup.sector.entity.Sector;
import com.climbup.climbup.session.entity.UserSession;
import com.climbup.climbup.session.exception.UserSessionNotFoundException;
import com.climbup.climbup.session.repository.UserSessionRepository;
import com.climbup.climbup.user.entity.User;
import com.climbup.climbup.user.exception.UserNotFoundException;
import com.climbup.climbup.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService{
    private static final byte MAX_NUM_OF_RECS = 30;

    private final UserSessionRepository userSessionRepository;
    private final UserRepository userRepository;
    private final RouteMissionRepository routeMissionRepository;
    private final RecommendationRepository recommendationRepository;

    @Override
    @Transactional
    public void generateRecommendationsForSession(UserSession session){
        User user = session.getUser();

        List<RouteMission> routeMissions = routeMissionRepository.findUnattemptedRouteMissionsByUser(user.getId());

        AtomicInteger index = new AtomicInteger(0);

        List<ChallengeRecommendation> recommendations = routeMissions.stream()
                .map(routeMission -> {
                    ChallengeRecommendation recommendation = ChallengeRecommendation.builder()
                        .session(session)
                        .mission(routeMission)
                        .recommendedOrder(index.getAndIncrement())
                        .build();

                    return recommendation;
                })
                .toList();

        recommendations = recommendations.subList(0, (int) Math.min(MAX_NUM_OF_RECS, (long) recommendations.size()));
        recommendationRepository.saveAll(recommendations);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RouteMissionRecommendationResponse> getRecommendationsByUserActiveSession(Long userId) {
        UserSession session = userSessionRepository.findByUserIdAndEndedAtIsNull(userId).orElseThrow(UserSessionNotFoundException::new);

        List<ChallengeRecommendation> recommendations = recommendationRepository.findBySession(session);

        return recommendations.stream().map(recommendation -> {
            RouteMission routeMission = recommendation.getMission();
            ClimbingGym gym = routeMission.getGym();
            List<UserMissionAttempt> attempts = routeMission.getAttempts().stream().toList();
            Sector sector = routeMission.getSector();

            return RouteMissionRecommendationResponse.toDto(recommendation, routeMission, gym, attempts, sector, recommendation.getRecommendedOrder());
        }).toList();
    }
}
