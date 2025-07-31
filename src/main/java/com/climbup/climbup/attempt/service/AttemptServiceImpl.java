package com.climbup.climbup.attempt.service;

import com.climbup.climbup.attempt.dto.LevelSRReward;
import com.climbup.climbup.attempt.dto.request.CreateAttemptRequest;
import com.climbup.climbup.attempt.dto.response.CreateAttemptResponse;
import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import com.climbup.climbup.attempt.repository.UserMissionAttemptRepository;
import com.climbup.climbup.common.exception.ErrorCode;
import com.climbup.climbup.common.exception.ValidationException;
import com.climbup.climbup.route.entity.RouteMission;
import com.climbup.climbup.route.exception.RouteNotFoundException;
import com.climbup.climbup.route.repository.RouteMissionRepository;
import com.climbup.climbup.session.entity.UserSession;
import com.climbup.climbup.session.exception.UserSessionNotFoundException;
import com.climbup.climbup.session.repository.UserSessionRepository;
import com.climbup.climbup.sr.entity.SRHistory;
import com.climbup.climbup.sr.repository.SRHistoryRepository;
import com.climbup.climbup.user.entity.User;
import com.climbup.climbup.user.exception.UserNotFoundException;
import com.climbup.climbup.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AttemptServiceImpl implements AttemptService {

    private final UserMissionAttemptRepository attemptRepository;
    private final RouteMissionRepository routeMissionRepository;
    private final UserRepository userRepository;
    private final UserSessionRepository sessionRepository;
    private final SRHistoryRepository srHistoryRepository;

    @Transactional
    public CreateAttemptResponse createAttempt(Long userId, CreateAttemptRequest request) {
        log.info("Creating attempt for user: {}, mission: {}, success: {}", userId, request.getMissionId(), request.getSuccess());

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        RouteMission mission = routeMissionRepository.findById(request.getMissionId())
                .orElseThrow(RouteNotFoundException::new);

        UserSession activeSession = sessionRepository.findByUserIdAndEndedAtIsNull(userId)
                .orElseThrow(UserSessionNotFoundException::new);

        UserMissionAttempt attempt = UserMissionAttempt.builder()
                .user(user)
                .session(activeSession)
                .mission(mission)
                .success(request.getSuccess())
                .build();

        attempt = attemptRepository.save(attempt);

        Integer srGained = 0;
        Integer currentSr = user.getSr();

        if (request.getSuccess()) {
            try {
                LevelSRReward reward = LevelSRReward.fromLevelName(mission.getDifficulty());
                srGained = reward.getSrReward();

                Integer srBefore = user.getSr();
                Integer srAfter = srBefore + srGained;

                user.updateSr(srAfter);
                currentSr = srAfter;

                SRHistory srHistory = SRHistory.builder()
                        .user(user)
                        .session(activeSession)
                        .mission(mission)
                        .srBefore(srBefore)
                        .srAfter(srAfter)
                        .build();

                srHistoryRepository.save(srHistory);

                log.info("SR updated for user: {} from {} to {} (+{}) for difficulty: {}",
                        userId, srBefore, srAfter, srGained, mission.getDifficulty());
            } catch (IllegalArgumentException e) {
                log.warn("Invalid difficulty level: {} for mission: {}", mission.getDifficulty(), mission.getId());
                throw new ValidationException(ErrorCode.INVALID_DIFFICULTY_LEVEL, mission.getDifficulty());
            }
        }

        return CreateAttemptResponse.builder()
                .missionAttemptId(attempt.getId())
                .success(attempt.getSuccess())
                .videoUrl(attempt.getVideoUrl())
                .createdAt(attempt.getCreatedAt())
                .srGained(srGained)
                .currentSr(currentSr)
                .build();
    }
}