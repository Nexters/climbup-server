package com.climbup.climbup.user.service;

public interface OnboardingService {
    void completeOnboarding(Long userId, Long gymId, Long gymLevelId);
    void setUserGym(Long userId, Long gymId);
    void setUserGymLevel(Long userId, Long gymLevelId);
}