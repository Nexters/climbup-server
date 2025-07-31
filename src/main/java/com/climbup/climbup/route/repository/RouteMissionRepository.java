package com.climbup.climbup.route.repository;

import com.climbup.climbup.route.entity.RouteMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteMissionRepository extends JpaRepository<RouteMission, Long> {
}
