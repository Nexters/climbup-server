package com.climbup.climbup.route.repository;

import com.climbup.climbup.route.entity.RouteMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteMissionRepository extends JpaRepository<RouteMission, Long> {

    @Query("""
        SELECT rm
        FROM RouteMission rm
        WHERE NOT EXISTS (
            SELECT 1
            FROM UserMissionAttempt a
            WHERE a.mission = rm
              AND a.user.id = :userId
              AND a.success = TRUE
        )
    """)
    List<RouteMission> findUnattemptedRouteMissionsByUser(@Param("userId") Long userId);

    Optional<RouteMission> findByIdAndRemovedAtIsNull(Long id);

    @Query("SELECT rm FROM RouteMission rm " +
            "WHERE rm.removedAt IS NULL " +
            "AND (:gymId IS NULL OR rm.gym.id = :gymId) " +
            "AND (:sectorId IS NULL OR rm.sector.id = :sectorId) " +
            "AND (:difficulty IS NULL OR rm.difficulty = :difficulty) " +
            "ORDER BY rm.postedAt DESC")
    List<RouteMission> findRouteMissionsWithFilters(
            @Param("gymId") Long gymId,
            @Param("sectorId") Long sectorId,
            @Param("difficulty") String difficulty
    );
}
