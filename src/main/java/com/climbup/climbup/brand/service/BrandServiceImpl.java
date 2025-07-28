package com.climbup.climbup.brand.service;

import com.climbup.climbup.brand.dto.response.BrandResponse;
import com.climbup.climbup.brand.entity.Brand;
import com.climbup.climbup.brand.exception.BrandNotFoundException;
import com.climbup.climbup.brand.repository.BrandRepository;
import com.climbup.climbup.gym.dto.response.GymLevelResponse;
import com.climbup.climbup.gym.dto.response.GymResponse;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.gym.entity.GymLevel;
import com.climbup.climbup.gym.repository.ClimbingGymRepository;
import com.climbup.climbup.gym.repository.GymLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final GymLevelRepository gymLevelRepository;
    private final ClimbingGymRepository climbingGymRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BrandResponse> getAllBrands() {
        List<Brand> brands = brandRepository.findAllWithGyms();
        return brands.stream()
                .map(BrandResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GymResponse> getBrandGyms(Long brandId) {
        brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException());

        List<ClimbingGym> gyms = climbingGymRepository.findByBrandIdWithBrand(brandId);
        return gyms.stream()
                .map(GymResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GymLevelResponse> getBrandLevels(Long brandId) {
        brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException());

        List<GymLevel> gymLevels = gymLevelRepository.findByBrandIdOrderBySortOrder(brandId);
        return gymLevels.stream()
                .map(GymLevelResponse::fromEntity)
                .toList();
    }
}