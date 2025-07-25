package com.climbup.climbup.gym.service;

import com.climbup.climbup.gym.dto.response.GymResponse;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.gym.repository.ClimbingGymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GymServiceImpl implements GymService {

    private final ClimbingGymRepository climbingGymRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GymResponse> getAllGyms() {
        List<ClimbingGym> gyms = climbingGymRepository.findAll();
        return gyms.stream().map(GymResponse::fromEntity).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClimbingGym getGymById(Long id) {
        return climbingGymRepository.findById(id).orElseThrow();
    }
}
