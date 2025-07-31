package com.climbup.climbup.sr.repository;

import com.climbup.climbup.sr.entity.SRHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SRHistoryRepository extends JpaRepository<SRHistory, Long> {
}