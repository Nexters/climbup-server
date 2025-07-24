package com.climbup.climbup.tier.repository;

import com.climbup.climbup.tier.entity.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TierRepository extends JpaRepository<Tier, Long> {
}
