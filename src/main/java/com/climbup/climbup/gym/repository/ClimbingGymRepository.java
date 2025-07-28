package com.climbup.climbup.gym.repository;

import com.climbup.climbup.gym.entity.ClimbingGym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClimbingGymRepository extends JpaRepository<ClimbingGym, Long> {

    @Query("SELECT g FROM ClimbingGym g JOIN FETCH g.brand")
    List<ClimbingGym> findAllWithBrand();

    @Query("SELECT g FROM ClimbingGym g JOIN FETCH g.brand WHERE g.id = :id")
    Optional<ClimbingGym> findByIdWithBrand(@Param("id") Long id);

    @Query("SELECT g FROM ClimbingGym g JOIN FETCH g.brand WHERE g.brand.name = :brandName")
    List<ClimbingGym> findByBrandNameWithBrand(@Param("brandName") String brandName);

    @Query("SELECT g FROM ClimbingGym g JOIN FETCH g.brand WHERE g.brand.id = :brandId")
    List<ClimbingGym> findByBrandIdWithBrand(@Param("brandId") Long brandId);
}