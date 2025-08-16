package com.climbup.climbup.attempt.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LevelSRReward {
    V0("V0", 1),
    V1("V1", 10),
    V2("V2", 20),
    V3("V3", 30),
    V4("V4", 100),
    V5("V5", 200),
    V6("V6", 300),
    V7("V7", 400),
    V8("V8", 500),
    V9("V9", 600),
    V10("V10", 700),
    GREEN("GREEN", 10),
    BLUE("BLUE", 30),
    RED("RED", 100),
    PURPLE("PURPLE", 200);

    private final String levelName;
    private final Integer srReward;

    public static LevelSRReward fromLevelName(String levelName) {
        for (LevelSRReward reward : values()) {
            if (reward.levelName.equals(levelName)) {
                return reward;
            }
        }
        throw new IllegalArgumentException("Unknown level name: " + levelName);
    }
}