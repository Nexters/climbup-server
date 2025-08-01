package com.climbup.climbup.route.repository;

import com.climbup.climbup.route.entity.RouteMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteMissionRepository extends JpaRepository<RouteMission, Long> {
    @Query("SELECT rm from RouteMission rm LEFT JOIN rm.attempts uma on uma.user.id = :userId WHERE uma.id IS NULL")
    List<RouteMission> findUnattemptedRouteMissionsByUser(@Param("userId") Long userId);
}
