package com.climbup.climbup.test.controller;

import com.climbup.climbup.brand.entity.Brand;
import com.climbup.climbup.brand.repository.BrandRepository;
import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.gym.entity.GymLevel;
import com.climbup.climbup.gym.repository.ClimbingGymRepository;
import com.climbup.climbup.gym.repository.GymLevelRepository;
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
    private final BrandRepository brandRepository;
    private final GymLevelRepository gymLevelRepository;

    @Operation(summary = "랜덤 숫자 생성", description = "0-99 사이의 랜덤한 정수를 반환합니다")
    @ApiResponse(responseCode = "200", description = "성공적으로 랜덤 숫자를 생성함")
    @GetMapping
    public ResponseEntity<ApiResult<Map<String, Object>>> getRandomNumber() {
        Map<String, Object> response = new HashMap<>();
        response.put("randomNumber", (int)(Math.random() * 100));
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @Operation(summary = "테스트 데이터 초기화", description = "개발/테스트용 브랜드, 레벨, 암장, 브랜드별 레벨 데이터를 생성합니다")
    @ApiResponse(responseCode = "200", description = "테스트 데이터 생성 완료")
    @PostMapping("/init-data")
    @Transactional
    public ResponseEntity<ApiResult<Map<String, Object>>> initTestData() {
        int brandsCreated = 0;
        int levelsCreated = 0;
        int gymsCreated = 0;
        int gymLevelsCreated = 0;

        // 1. 브랜드 데이터 생성
        if (brandRepository.count() == 0) {
            List<Brand> brands = brandRepository.saveAll(List.of(
                    Brand.builder()
                            .name("더클라임")
                            .description("국내 최대 클라이밍 체인")
                            .logoUrl("https://example.com/the-climb-logo.png")
                            .build(),
                    Brand.builder()
                            .name("클라이밍파크")
                            .description("프리미엄 클라이밍 센터")
                            .logoUrl("https://example.com/climbing-park-logo.png")
                            .build()
            ));
            brandsCreated = brands.size();
        }

        // 2. 기본 레벨 데이터 생성
        if (levelRepository.count() == 0) {
            List<Level> levels = levelRepository.saveAll(List.of(
                    Level.builder()
                            .name("V0")
                            .description("입문자용 가장 쉬운 난이도")
                            .sortOrder(0)
                            .build(),
                    Level.builder()
                            .name("V1")
                            .description("초급자용 난이도")
                            .sortOrder(1)
                            .build(),
                    Level.builder()
                            .name("V2")
                            .description("초급자 상급 난이도")
                            .sortOrder(2)
                            .build(),
                    Level.builder()
                            .name("V3")
                            .description("중급자 입문 난이도")
                            .sortOrder(3)
                            .build(),
                    Level.builder()
                            .name("V4")
                            .description("중급자 난이도")
                            .sortOrder(4)
                            .build(),
                    Level.builder()
                            .name("V5")
                            .description("중급자 상급 난이도")
                            .sortOrder(5)
                            .build(),
                    Level.builder()
                            .name("V6")
                            .description("고급자 입문 난이도")
                            .sortOrder(6)
                            .build(),
                    Level.builder()
                            .name("V7")
                            .description("고급자 난이도")
                            .sortOrder(7)
                            .build(),
                    Level.builder()
                            .name("V8")
                            .description("고급자 상급 난이도")
                            .sortOrder(8)
                            .build(),
                    Level.builder()
                            .name("V9")
                            .description("전문가 난이도")
                            .sortOrder(9)
                            .build(),
                    Level.builder()
                            .name("V10")
                            .description("최고 난이도")
                            .sortOrder(10)
                            .build()
            ));
            levelsCreated = levels.size();
        }

        // 3. 암장 데이터 생성
        if (climbingGymRepository.count() == 0) {
            Brand theClimb = brandRepository.findByName("더클라임").orElseThrow();
            Brand climbingPark = brandRepository.findByName("클라이밍파크").orElseThrow();

            List<ClimbingGym> gyms = climbingGymRepository.saveAll(List.of(
                    ClimbingGym.builder()
                            .brand(theClimb)
                            .branchName("강남점")
                            .address("서울특별시 강남구 테헤란로8길 21 지하 1층")
                            .sectorInfo("지하1층~3층, 총 4개층")
                            .imageUrl("https://example.com/gym1.jpg")
                            .build(),
                    ClimbingGym.builder()
                            .brand(theClimb)
                            .branchName("홍대점")
                            .address("서울특별시 마포구 353-5 경남관광빌딩 2층")
                            .sectorInfo("지하1층~2층, 총 3개층")
                            .imageUrl("https://example.com/gym2.jpg")
                            .build(),
                    ClimbingGym.builder()
                            .brand(climbingPark)
                            .branchName("잠실점")
                            .address("서울특별시 송파구 올림픽로 300")
                            .sectorInfo("1층~3층, 총 3개층")
                            .imageUrl("https://example.com/gym3.jpg")
                            .build()
            ));
            gymsCreated = gyms.size();
        }

        // 4. 브랜드별 레벨 체계 생성
        if (gymLevelRepository.count() == 0) {
            Brand theClimb = brandRepository.findByName("더클라임").orElseThrow();
            Brand climbingPark = brandRepository.findByName("클라이밍파크").orElseThrow();

            Level v0 = levelRepository.findAll().stream()
                    .filter(l -> l.getName().equals("V0")).findFirst().orElseThrow();
            Level v1 = levelRepository.findAll().stream()
                    .filter(l -> l.getName().equals("V1")).findFirst().orElseThrow();
            Level v3 = levelRepository.findAll().stream()
                    .filter(l -> l.getName().equals("V3")).findFirst().orElseThrow();
            Level v4 = levelRepository.findAll().stream()
                    .filter(l -> l.getName().equals("V4")).findFirst().orElseThrow();
            Level v6 = levelRepository.findAll().stream()
                    .filter(l -> l.getName().equals("V6")).findFirst().orElseThrow();
            Level v8 = levelRepository.findAll().stream()
                    .filter(l -> l.getName().equals("V8")).findFirst().orElseThrow();

            // 더클라임 레벨 체계
            List<GymLevel> theClimbLevels = List.of(
                    GymLevel.builder()
                            .brand(theClimb)
                            .level(v0)
                            .displayName("ORANGE")
                            .srMin(600)
                            .srMax(649)
                            .sortOrder(1)
                            .imageUrl("https://example.com/orange-level.png")
                            .build(),
                    GymLevel.builder()
                            .brand(theClimb)
                            .level(v1)
                            .displayName("GREEN")
                            .srMin(650)
                            .srMax(999)
                            .sortOrder(2)
                            .imageUrl("https://example.com/green-level.png")
                            .build(),
                    GymLevel.builder()
                            .brand(theClimb)
                            .level(v3)
                            .displayName("BLUE")
                            .srMin(1000)
                            .srMax(1999)
                            .sortOrder(3)
                            .imageUrl("https://example.com/blue-level.png")
                            .build(),
                    GymLevel.builder()
                            .brand(theClimb)
                            .level(v4)
                            .displayName("RED")
                            .srMin(2000)
                            .srMax(2999)
                            .sortOrder(4)
                            .imageUrl("https://example.com/red-level.png")
                            .build(),
                    GymLevel.builder()
                            .brand(theClimb)
                            .level(v6)
                            .displayName("PURPLE")
                            .srMin(3000)
                            .srMax(null)
                            .sortOrder(5)
                            .imageUrl("https://example.com/purple-level.png")
                            .build()
            );

            gymLevelRepository.saveAll(theClimbLevels);
            gymLevelsCreated = theClimbLevels.size();
        }

        // 결과 응답
        Map<String, Object> response = new HashMap<>();
        response.put("message", "테스트 데이터가 생성되었습니다.");
        response.put("brandsCreated", brandsCreated);
        response.put("levelsCreated", levelsCreated);
        response.put("gymsCreated", gymsCreated);
        response.put("gymLevelsCreated", gymLevelsCreated);
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @Operation(summary = "데이터 현황 조회", description = "현재 DB에 있는 모든 데이터 개수를 확인합니다")
    @ApiResponse(responseCode = "200", description = "데이터 현황 조회 완료")
    @GetMapping("/data-status")
    public ResponseEntity<ApiResult<Map<String, Object>>> getDataStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("brandCount", brandRepository.count());
        response.put("levelCount", levelRepository.count());
        response.put("gymCount", climbingGymRepository.count());
        response.put("gymLevelCount", gymLevelRepository.count());
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @Operation(summary = "테스트 데이터 삭제", description = "모든 테스트 데이터를 삭제합니다 (개발용)")
    @ApiResponse(responseCode = "200", description = "테스트 데이터 삭제 완료")
    @PostMapping("/clear-data")
    @Transactional
    public ResponseEntity<ApiResult<Map<String, Object>>> clearTestData() {
        long brandCount = brandRepository.count();
        long levelCount = levelRepository.count();
        long gymCount = climbingGymRepository.count();
        long gymLevelCount = gymLevelRepository.count();

        // 외래키 제약조건 때문에 순서가 중요함
        gymLevelRepository.deleteAll();
        climbingGymRepository.deleteAll();
        levelRepository.deleteAll();
        brandRepository.deleteAll();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "테스트 데이터가 삭제되었습니다.");
        response.put("deletedBrands", brandCount);
        response.put("deletedLevels", levelCount);
        response.put("deletedGyms", gymCount);
        response.put("deletedGymLevels", gymLevelCount);
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(ApiResult.success(response));
    }

    @Operation(summary = "브랜드별 레벨 현황 조회", description = "각 브랜드별로 설정된 레벨 체계를 확인합니다")
    @ApiResponse(responseCode = "200", description = "브랜드별 레벨 현황 조회 완료")
    @GetMapping("/brand-levels")
    public ResponseEntity<ApiResult<Map<String, Object>>> getBrandLevels() {
        Map<String, Object> response = new HashMap<>();

        List<Brand> brands = brandRepository.findAll();
        for (Brand brand : brands) {
            List<GymLevel> gymLevels = gymLevelRepository.findByBrandIdOrderBySortOrder(brand.getId());
            response.put(brand.getName() + "_levels", gymLevels.size());
            response.put(brand.getName() + "_gyms", brand.getGyms().size());
        }

        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(ApiResult.success(response));
    }
}