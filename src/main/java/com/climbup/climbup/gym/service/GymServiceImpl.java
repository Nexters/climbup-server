package com.climbup.climbup.gym.service;

import com.climbup.climbup.brand.repository.BrandRepository;
import com.climbup.climbup.gym.dto.response.GymResponse;
import com.climbup.climbup.gym.dto.response.GymLevelResponse;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.gym.entity.GymLevel;
import com.climbup.climbup.gym.exception.GymNotFoundException;
import com.climbup.climbup.gym.repository.ClimbingGymRepository;
import com.climbup.climbup.gym.repository.GymLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GymServiceImpl implements GymService {

    private final ClimbingGymRepository climbingGymRepository;
    private final GymLevelRepository gymLevelRepository;
    private final BrandRepository brandRepository;

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
    public List<GymResponse> getGymsByBrandName(String brandName) {
        List<ClimbingGym> gyms = climbingGymRepository.findByBrandNameWithBrand(brandName);
        return gyms.stream()
                .map(GymResponse::fromEntity)
                .toList();
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