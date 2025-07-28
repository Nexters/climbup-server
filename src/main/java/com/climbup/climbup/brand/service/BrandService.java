package com.climbup.climbup.brand.service;

import com.climbup.climbup.brand.dto.response.BrandResponse;
import com.climbup.climbup.gym.dto.response.GymLevelResponse;
import com.climbup.climbup.gym.dto.response.GymResponse;

import java.util.List;

public interface BrandService {
    List<BrandResponse> getAllBrands();
    List<GymResponse> getBrandGyms(Long brandId);
    List<GymLevelResponse> getBrandLevels(Long brandId);
}
