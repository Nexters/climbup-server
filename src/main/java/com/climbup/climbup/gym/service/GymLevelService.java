package com.climbup.climbup.gym.service;

import com.climbup.climbup.gym.dto.response.GymLevelResponse;

public interface GymLevelService {
    GymLevelResponse getGymLevelById(Long gymLevelId);
}
