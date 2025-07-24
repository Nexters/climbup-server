package com.climbup.climbup.tier.service;

import com.climbup.climbup.tier.dto.response.TierResponse;
import com.climbup.climbup.tier.entity.Tier;

import java.util.List;

public interface TierService {
    List<TierResponse> getAllTiers();

    Tier getTierById(Long id);
}
