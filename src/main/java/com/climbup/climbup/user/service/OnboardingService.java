package com.climbup.climbup.user.service;

public interface OnboardingService {

    void completeOnboarding(Long userId, Long gymId, Long levelId);

    void setUserGym(Long userId, Long gymId);

    void setUserLevel(Long userId, Long levelId);
}