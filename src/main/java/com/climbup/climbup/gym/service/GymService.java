package com.climbup.climbup.gym.service;

import com.climbup.climbup.gym.dto.response.GymResponse;
import com.climbup.climbup.gym.dto.response.GymLevelResponse;

import java.util.List;

public interface GymService {
    List<GymResponse> getAllGyms();
    GymResponse getGymById(Long gymId);
    List<GymResponse> getGymsByBrandName(String brandName);
    List<GymResponse> getGymsByBrandId(Long brandId);
}