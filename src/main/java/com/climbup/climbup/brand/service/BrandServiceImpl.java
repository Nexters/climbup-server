package com.climbup.climbup.brand.service;

import com.climbup.climbup.brand.dto.response.BrandResponse;
import com.climbup.climbup.brand.entity.Brand;
import com.climbup.climbup.brand.exception.BrandNotFoundException;
import com.climbup.climbup.brand.repository.BrandRepository;
import com.climbup.climbup.gym.dto.response.GymLevelResponse;
import com.climbup.climbup.gym.entity.GymLevel;
import com.climbup.climbup.gym.exception.GymLevelNotFoundException;
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
    public List<GymLevelResponse> getBrandLevels(Long brandId) {
        brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException());

        List<GymLevel> gymLevels = gymLevelRepository.findByBrandIdOrderBySortOrder(brandId);
        return gymLevels.stream()
                .map(GymLevelResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GymLevelResponse getBrandLevel(Long brandId, Long levelId) {
        brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException());

        GymLevel gymLevel = gymLevelRepository.findByIdAndBrandId(levelId, brandId)
                .orElseThrow(() -> new GymLevelNotFoundException("해당 브랜드에서 존재하지 않는 레벨입니다."));

        return GymLevelResponse.fromEntity(gymLevel);
    }
}