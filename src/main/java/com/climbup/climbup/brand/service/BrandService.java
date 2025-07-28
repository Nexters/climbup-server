package com.climbup.climbup.brand.service;

import com.climbup.climbup.brand.dto.response.BrandResponse;
import com.climbup.climbup.gym.dto.response.GymLevelResponse;

import java.util.List;

public interface BrandService {
    List<BrandResponse> getAllBrands();
    List<GymLevelResponse> getBrandLevels(Long brandId);
    GymLevelResponse getBrandLevel(Long brandId, Long levelId);
}
