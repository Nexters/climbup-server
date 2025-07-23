package com.climbup.climbup.tier.service;

import com.climbup.climbup.tier.dto.response.TierResponse;
import com.climbup.climbup.tier.entity.Tier;
import com.climbup.climbup.tier.repository.TierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TierServiceImpl implements TierService {

    private final TierRepository tierRepository;

    @Override
    @Transactional()
    public List<TierResponse> getAllTiers() {
        List<Tier> tiers = tierRepository.findAll();

        return tiers.stream().map(TierResponse::toDto).toList();
    }
}
