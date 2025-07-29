package com.climbup.climbup.gym.repository;

import com.climbup.climbup.gym.entity.GymLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GymLevelRepository extends JpaRepository<GymLevel, Long> {

    @Query("SELECT gl FROM GymLevel gl " +
            "JOIN FETCH gl.brand " +
            "JOIN FETCH gl.level " +
            "WHERE gl.brand.id = :brandId " +
            "ORDER BY gl.sortOrder")
    List<GymLevel> findByBrandIdOrderBySortOrder(@Param("brandId") Long brandId);

    @Query("SELECT gl FROM GymLevel gl " +
            "JOIN FETCH gl.brand " +
            "JOIN FETCH gl.level " +
            "WHERE gl.id = :id AND gl.brand.id = :brandId")
    Optional<GymLevel> findByIdAndBrandId(@Param("id") Long id, @Param("brandId") Long brandId);
}