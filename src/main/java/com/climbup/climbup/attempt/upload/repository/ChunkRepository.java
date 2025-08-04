package com.climbup.climbup.attempt.upload.repository;

import com.climbup.climbup.attempt.upload.entity.Chunk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChunkRepository extends JpaRepository<Chunk, UUID> {
}