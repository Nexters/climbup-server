package com.climbup.climbup.level.repository;

import com.climbup.climbup.level.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Long> {
}