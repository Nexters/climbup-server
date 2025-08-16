package com.climbup.climbup.gym.service;

import com.climbup.climbup.gym.dto.response.GymAttemptResponse;
import com.climbup.climbup.gym.dto.response.GymResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GymService {
    List<GymResponse> getAllGyms();
    GymResponse getGymById(Long gymId);
    List<GymResponse> getGymsByBrandId(Long brandId);
    Page<GymAttemptResponse> getSuccessfulAttemptsByGym(Long gymId, Long userId, Pageable pageable);

}