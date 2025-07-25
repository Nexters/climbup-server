package com.climbup.climbup.user.service;

import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.gym.repository.ClimbingGymRepository;
import com.climbup.climbup.level.entity.Level;
import com.climbup.climbup.level.repository.LevelRepository;
import com.climbup.climbup.user.entity.User;
import com.climbup.climbup.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OnboardingServiceImpl implements OnboardingService {

    private final UserRepository userRepository;
    private final LevelRepository levelRepository;
    private final ClimbingGymRepository climbingGymRepository;

    @Override
    @Transactional
    public void completeOnboarding(Long userId, Long gymId, Long levelId) {
        User user = findUserById(userId);

        if (user.getGym() == null && gymId != null) {
            ClimbingGym gym = findGymById(gymId);
            user.selectGym(gym);
        }

        if (user.getLevel() == null && levelId != null) {
            Level level = findLevelById(levelId);
            user.selectLevel(level);
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void setUserGym(Long userId, Long gymId) {
        User user = findUserById(userId);
        ClimbingGym gym = findGymById(gymId);
        user.selectGym(gym);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void setUserLevel(Long userId, Long levelId) {
        User user = findUserById(userId);
        Level level = findLevelById(levelId);
        user.selectLevel(level);
        userRepository.save(user);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    private ClimbingGym findGymById(Long gymId) {
        return climbingGymRepository.findById(gymId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 암장입니다."));
    }

    private Level findLevelById(Long levelId) {
        return levelRepository.findById(levelId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 레벨입니다."));
    }
}