package com.climbup.climbup.level.repository;

import com.climbup.climbup.level.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {

    Optional<Level> findByName(String name);
}
