package com.climbup.climbup.user.service;

import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.gym.entity.GymLevel;
import com.climbup.climbup.gym.exception.GymLevelBrandMismatchException;
import com.climbup.climbup.gym.exception.GymLevelNotFoundException;
import com.climbup.climbup.gym.exception.GymNotFoundException;
import com.climbup.climbup.gym.repository.ClimbingGymRepository;
import com.climbup.climbup.gym.repository.GymLevelRepository;
import com.climbup.climbup.user.entity.User;
import com.climbup.climbup.user.exception.UserNotFoundException;
import com.climbup.climbup.user.exception.UserOnboardingAlreadyCompleteException;
import com.climbup.climbup.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OnboardingServiceImpl implements OnboardingService {

    private final UserRepository userRepository;
    private final GymLevelRepository gymLevelRepository;
    private final ClimbingGymRepository climbingGymRepository;

    @Override
    @Transactional
    public void completeOnboarding(Long userId, Long gymId, Long gymLevelId) {
        User user = findUserById(userId);

        if (user.hasCompletedOnboarding()) {
            throw new UserOnboardingAlreadyCompleteException();
        }

        if (user.getGym() == null && gymId != null) {
            ClimbingGym gym = findGymById(gymId);
            user.selectGym(gym);
        }

        if (user.getGymLevel() == null && gymLevelId != null) {
            GymLevel gymLevel = findGymLevelById(gymLevelId);

            // 암장과 레벨이 같은 브랜드인지 검증
            if (user.getGym() != null &&
                    !gymLevel.getBrand().getId().equals(user.getGym().getBrand().getId())) {
                throw new GymLevelBrandMismatchException();
            }

            user.selectGymLevel(gymLevel);
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void setUserGym(Long userId, Long gymId) {
        User user = findUserById(userId);

        if (user.hasCompletedOnboarding()) {
            throw new UserOnboardingAlreadyCompleteException("이미 암장을 선택한 사용자입니다.");
        }

        ClimbingGym gym = findGymById(gymId);
        user.selectGym(gym);

        // 기존 레벨이 새로운 암장의 브랜드와 맞지 않으면 레벨 초기화
        if (user.getGymLevel() != null &&
                !user.getGymLevel().getBrand().getId().equals(gym.getBrand().getId())) {
            user.selectGymLevel(null);
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void setUserGymLevel(Long userId, Long gymLevelId) {
        User user = findUserById(userId);

        if (user.hasCompletedOnboarding()) {
            throw new UserOnboardingAlreadyCompleteException("이미 암장 레벨을 선택한 사용자입니다.");
        }

        GymLevel gymLevel = findGymLevelById(gymLevelId);

        if (user.getGym() != null &&
                !gymLevel.getBrand().getId().equals(user.getGym().getBrand().getId())) {
            throw new GymLevelBrandMismatchException();
        }

        user.selectGymLevel(gymLevel);
        userRepository.save(user);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
    }

    private ClimbingGym findGymById(Long gymId) {
        return climbingGymRepository.findById(gymId)
                .orElseThrow(() -> new GymNotFoundException());
    }

    private GymLevel findGymLevelById(Long gymLevelId) {
        return gymLevelRepository.findById(gymLevelId)
                .orElseThrow(() -> new GymLevelNotFoundException());
    }
}