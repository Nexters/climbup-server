package com.climbup.climbup.auth.util;

import java.util.List;
import java.util.Random;

public class RandomNicknameGenerator {

    private static final List<String> ADJECTIVES = List.of(
            "재빠른", "용감한", "용맹한", "끈질긴", "다정한", "당당한", "당황한", "귀여운", "명랑한", "날쌘", "좀치는", "똑똑한"
    );

    private static final List<String> ANIMALS = List.of(
            "고양이", "다람쥐", "꽃사슴", "여우", "독수리", "팬더", "고릴라", "햄스터", "너구리", "치타", "돌고래", "오랑우탄", "카피바라"
    );

    private static final Random RANDOM = new Random();

    public static String generate() {
        String adjective = ADJECTIVES.get(RANDOM.nextInt(ADJECTIVES.size()));
        String animal = ANIMALS.get(RANDOM.nextInt(ANIMALS.size()));
        return adjective + animal;
    }
}
