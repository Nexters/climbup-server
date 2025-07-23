package com.climbup.climbup.tier.service;

import com.climbup.climbup.tier.dto.response.TierResponse;

import java.util.List;

public interface TierService {
    List<TierResponse> getAllTiers();
}
