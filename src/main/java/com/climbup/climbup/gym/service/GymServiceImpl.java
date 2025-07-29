package com.climbup.climbup.gym.service;

import com.climbup.climbup.gym.dto.response.GymResponse;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.gym.exception.GymNotFoundException;
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
        List<ClimbingGym> gyms = climbingGymRepository.findAllWithBrand();
        return gyms.stream()
                .map(GymResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GymResponse getGymById(Long gymId) {
        ClimbingGym gym = climbingGymRepository.findByIdWithBrand(gymId)
                .orElseThrow(() -> new GymNotFoundException());
        return GymResponse.fromEntity(gym);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GymResponse> getGymsByBrandId(Long brandId) {
        List<ClimbingGym> gyms = climbingGymRepository.findByBrandIdWithBrand(brandId);
        return gyms.stream()
                .map(GymResponse::fromEntity)
                .toList();
    }
}