package com.climbup.climbup.gym.service;

import com.climbup.climbup.gym.dto.response.GymResponse;

import java.util.List;

public interface GymService {
    List<GymResponse> getAllGyms();
    GymResponse getGymById(Long gymId);
    List<GymResponse> getGymsByBrandId(Long brandId);
}