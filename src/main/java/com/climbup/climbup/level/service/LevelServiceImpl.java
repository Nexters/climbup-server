package com.climbup.climbup.level.service;

import com.climbup.climbup.level.dto.response.LevelResponse;
import com.climbup.climbup.level.entity.Level;
import com.climbup.climbup.level.repository.LevelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;

    @Override
    @Transactional
    public List<LevelResponse> getAllLevels() {
        List<Level> levels = levelRepository.findAll();

        return levels.stream().map(LevelResponse::fromEntity).toList();
    }

    @Override
    @Transactional
    public Level getLevelById(Long id){
        return levelRepository.findById(id).orElseThrow();
    }
}
