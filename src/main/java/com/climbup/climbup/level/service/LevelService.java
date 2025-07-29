package com.climbup.climbup.level.service;

import com.climbup.climbup.level.dto.response.LevelResponse;

import java.util.List;

public interface LevelService {
    List<LevelResponse> getAllLevels();

    LevelResponse getLevelById(Long id);
}
