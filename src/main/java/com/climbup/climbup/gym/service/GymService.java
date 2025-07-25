package com.climbup.climbup.gym.service;

import com.climbup.climbup.gym.dto.response.GymResponse;
import com.climbup.climbup.gym.entity.ClimbingGym;

import java.util.List;

public interface GymService {
    List<GymResponse> getAllGyms();
    ClimbingGym getGymById(Long id);
}