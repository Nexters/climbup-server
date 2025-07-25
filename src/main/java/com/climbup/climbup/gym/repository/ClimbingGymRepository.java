package com.climbup.climbup.gym.repository;

import com.climbup.climbup.gym.entity.ClimbingGym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClimbingGymRepository extends JpaRepository<ClimbingGym, Long> {
}