package com.climbup.climbup.level.service;

import com.climbup.climbup.level.dto.response.LevelResponse;
import com.climbup.climbup.level.entity.Level;

import java.util.List;

public interface LevelService {
    List<LevelResponse> getAllLevels();

    Level getLevelById(Long id);
}
