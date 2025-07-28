package com.climbup.climbup.gym.service;

import com.climbup.climbup.gym.dto.response.GymLevelResponse;
import com.climbup.climbup.gym.entity.GymLevel;
import com.climbup.climbup.gym.exception.GymLevelNotFoundException;
import com.climbup.climbup.gym.repository.GymLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GymLevelServiceImpl implements GymLevelService{

    private final GymLevelRepository gymLevelRepository;

    @Override
    @Transactional(readOnly = true)
    public GymLevelResponse getGymLevelById(Long gymLevelId) {
        GymLevel gymLevel = gymLevelRepository.findById(gymLevelId)
                .orElseThrow(() -> new GymLevelNotFoundException());
        return GymLevelResponse.fromEntity(gymLevel);
    }
}
