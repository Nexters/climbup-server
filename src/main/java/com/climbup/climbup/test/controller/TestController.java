package com.climbup.climbup.test.controller;

import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.gym.repository.ClimbingGymRepository;
import com.climbup.climbup.level.entity.Level;
import com.climbup.climbup.level.repository.LevelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Test", description = "테스트용 API")
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final LevelRepository levelRepository;
    private final ClimbingGymRepository climbingGymRepository;

    @Operation(summary = "랜덤 숫자 생성", description = "0-99 사이의 랜덤한 정수를 반환합니다")
    @ApiResponse(responseCode = "200", description = "성공적으로 랜덤 숫자를 생성함")
    @GetMapping
    public ResponseEntity<ApiResult<Map<String, Object>>> getRandomNumber() {
        Map<String, Object> response = new HashMap<>();
        response.put("randomNumber", (int)(Math.random() * 100));
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @Operation(summary = "테스트 데이터 초기화", description = "개발/테스트용 기본 레벨 및 암장 데이터를 생성합니다")
    @ApiResponse(responseCode = "200", description = "테스트 데이터 생성 완료")
    @PostMapping("/init-data")
    @Transactional
    public ResponseEntity<ApiResult<Map<String, Object>>> initTestData() {
        int levelsCreated = 0;
        int gymsCreated = 0;

        // 레벨 데이터 생성 (전체 5개 레벨)
        if (levelRepository.count() == 0) {
            List<Level> levels = levelRepository.saveAll(List.of(
                    Level.builder()
                            .name("ORANGE")
                            .imageUrl("https://example.com/orange.png")
                            .srMin(600)
                            .srMax(649)
                            .build(),
                    Level.builder()
                            .name("GREEN")
                            .imageUrl("https://example.com/green.png")
                            .srMin(650)
                            .srMax(999)
                            .build(),
                    Level.builder()
                            .name("BLUE")
                            .imageUrl("https://example.com/blue.png")
                            .srMin(1000)
                            .srMax(2000)
                            .build(),
                    Level.builder()
                            .name("RED")
                            .imageUrl("https://example.com/red.png")
                            .srMin(2000)
                            .srMax(2999)
                            .build(),
                    Level.builder()
                            .name("PURPLE")
                            .imageUrl("https://example.com/purple.png")
                            .srMin(3000)
                            .srMax(null) // 상한 없음
                            .build()
            ));
            levelsCreated = levels.size();
        }

        // 암장 데이터 생성 (여러 암장)
        if (climbingGymRepository.count() == 0) {
            List<ClimbingGym> gyms = climbingGymRepository.saveAll(List.of(
                    ClimbingGym.builder()
                            .name("더클라임")
                            .location("강남점")
                            .address("서울특별시 강남구 테헤란로8길 21 지하 1층")
                            .sectorInfo("지하1층~3층, 총 4개층")
                            .imageUrl("https://example.com/gym1.jpg")
                            .build(),
                    ClimbingGym.builder()
                            .name("더클라임")
                            .location("홍대점")
                            .address("서울특별시 마포구 353-5 경남관광빌딩 2층")
                            .sectorInfo("지하1층~2층, 총 3개층")
                            .imageUrl("https://example.com/gym2.jpg")
                            .build()
            ));
            gymsCreated = gyms.size();
        }

        // 결과 응답
        Map<String, Object> response = new HashMap<>();
        response.put("message", "테스트 데이터가 생성되었습니다.");
        response.put("levelsCreated", levelsCreated);
        response.put("gymsCreated", gymsCreated);
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @Operation(summary = "데이터 현황 조회", description = "현재 DB에 있는 레벨/암장 데이터 개수를 확인합니다")
    @ApiResponse(responseCode = "200", description = "데이터 현황 조회 완료")
    @GetMapping("/data-status")
    public ResponseEntity<ApiResult<Map<String, Object>>> getDataStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("levelCount", levelRepository.count());
        response.put("gymCount", climbingGymRepository.count());
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @Operation(summary = "테스트 데이터 삭제", description = "모든 테스트 데이터를 삭제합니다 (개발용)")
    @ApiResponse(responseCode = "200", description = "테스트 데이터 삭제 완료")
    @PostMapping("/clear-data")
    @Transactional
    public ResponseEntity<ApiResult<Map<String, Object>>> clearTestData() {
        long levelCount = levelRepository.count();
        long gymCount = climbingGymRepository.count();

        levelRepository.deleteAll();
        climbingGymRepository.deleteAll();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "테스트 데이터가 삭제되었습니다.");
        response.put("deletedLevels", levelCount);
        response.put("deletedGyms", gymCount);
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(ApiResult.success(response));
    }
}