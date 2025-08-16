package com.climbup.climbup.test.controller;

import com.climbup.climbup.attempt.upload.service.UploadService;
import com.climbup.climbup.brand.entity.Brand;
import com.climbup.climbup.brand.repository.BrandRepository;
import com.climbup.climbup.common.dto.ApiResult;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.gym.entity.GymLevel;
import com.climbup.climbup.gym.repository.ClimbingGymRepository;
import com.climbup.climbup.gym.repository.GymLevelRepository;
import com.climbup.climbup.level.entity.Level;
import com.climbup.climbup.level.repository.LevelRepository;
import com.climbup.climbup.route.entity.RouteMission;
import com.climbup.climbup.route.repository.RouteMissionRepository;
import com.climbup.climbup.sector.entity.Sector;
import com.climbup.climbup.sector.repository.SectorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final RouteMissionRepository routeMissionRepository;
    private final SectorRepository sectorRepository;
    private final UploadService uploadService;

    @Operation(summary = "랜덤 숫자 생성", description = "0-99 사이의 랜덤한 정수를 반환합니다")
    @ApiResponse(responseCode = "200", description = "성공적으로 랜덤 숫자를 생성함")
    @GetMapping
    public ResponseEntity<ApiResult<Map<String, Object>>> getRandomNumber() {
        Map<String, Object> response = new HashMap<>();
        response.put("randomNumber", (int)(Math.random() * 100));
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(ApiResult.success(response));
    }

    @Operation(summary = "테스트 데이터 초기화", description = "개발/테스트용 브랜드, 레벨, 암장, 브랜드별 레벨, 섹터, 루트 미션 데이터를 생성합니다")
    @ApiResponse(responseCode = "200", description = "테스트 데이터 생성 완료")
    @PostMapping("/init-data")
    @Transactional
    public ResponseEntity<ApiResult<Map<String, Object>>> initTestData() {
        int brandsCreated = 0;
        int levelsCreated = 0;
        int gymsCreated = 0;
        int gymLevelsCreated = 0;
        int sectorsCreated = 0;
        int routeMissionsCreated = 0;

        // 1. 브랜드 데이터 생성
        if (brandRepository.count() == 0) {
            List<Brand> brands = brandRepository.saveAll(List.of(
                    Brand.builder()
                            .name("더클라임")
                            .description("국내 최대 클라이밍 체인")
                            .logoUrl("https://kr.object.ncloudstorage.com/holdy/images/brands/1755341339359_4ce3bc1f.png")
                            .build()
            ));
            brandsCreated = brands.size();
        }

        // 2. 기본 레벨 데이터 생성
        if (levelRepository.count() == 0) {
            List<Level> levels = levelRepository.saveAll(List.of(
                    Level.builder()
                            .name("입문자 단계")
                            .description("클라이밍이 처음이라면 가볍게 시작해보세요!")
                            .sortOrder(0)
                            .build(),
                    Level.builder()
                            .name("기본기를 익히는 단계")
                            .description("규칙도 익히고, 슬슬 재미가 붙은 단계네요!")
                            .sortOrder(1)
                            .build(),
                    Level.builder()
                            .name("V2 (미정)")
                            .sortOrder(2)
                            .build(),
                    Level.builder()
                            .name("도전의 재미가 시작되는 단계")
                            .description("도전의 재미가 본격적으로 느껴지실 거예요.")
                            .sortOrder(3)
                            .build(),
                    Level.builder()
                            .name("고난이도 루트에 도전하는 단계")
                            .description("이제 다양한 루트도 거뜬하죠? 멋져요!")
                            .sortOrder(4)
                            .build(),
                    Level.builder()
                            .name("V5 (미정)")
                            .sortOrder(5)
                            .build(),
                    Level.builder()
                            .name("어디서든 인정받는 단계")
                            .description("누구나 인정할 실력자시군요. 정말 대단해요!")
                            .sortOrder(6)
                            .build(),
                    Level.builder()
                            .name("V7 (미정)")
                            .sortOrder(7)
                            .build(),
                    Level.builder()
                            .name("V8 (미정)")
                            .sortOrder(8)
                            .build(),
                    Level.builder()
                            .name("V9 (미정)")
                            .description("전문가 난이도")
                            .sortOrder(9)
                            .build(),
                    Level.builder()
                            .name("V10 (미정)")
                            .description("최고 난이도")
                            .sortOrder(10)
                            .build()
            ));
            levelsCreated = levels.size();
        }

        // 3. 암장 데이터 생성
        if (climbingGymRepository.count() == 0) {
            Brand theClimb = brandRepository.findByName("더클라임").orElseThrow();

            List<ClimbingGym> gyms = climbingGymRepository.saveAll(List.of(
                    ClimbingGym.builder()
                            .brand(theClimb)
                            .branchName("강남점")
                            .address("서울특별시 강남구 테헤란로 8길 21 화인강남빌딩 지하 1층")
                            .sectorInfo("강남역 도보 4분 거리")
                            .imageUrl("https://kr.object.ncloudstorage.com/holdy/images/gyms/1755341292898_83b3b52b.png")
                            .build(),
                    ClimbingGym.builder()
                            .brand(theClimb)
                            .branchName("논현점")
                            .address("서울 서초구 강남대로 519, B1")
                            .sectorInfo("논혁연 4번 출구 도보 1분")
                            .imageUrl("https://kr.object.ncloudstorage.com/holdy/images/gyms/1755341319225_5837fa93.png")
                            .build()
            ));
            gymsCreated = gyms.size();
        }

        // 4. 브랜드별 레벨 체계 생성
        if (gymLevelRepository.count() == 0) {
            Brand theClimb = brandRepository.findByName("더클라임").orElseThrow();

            Level v0 = levelRepository.findAll().stream()
                    .filter(l -> l.getName().equals("입문자 단계")).findFirst().orElseThrow();
            Level v1 = levelRepository.findAll().stream()
                    .filter(l -> l.getName().equals("기본기를 익히는 단계")).findFirst().orElseThrow();
            Level v3 = levelRepository.findAll().stream()
                    .filter(l -> l.getName().equals("도전의 재미가 시작되는 단계")).findFirst().orElseThrow();
            Level v4 = levelRepository.findAll().stream()
                    .filter(l -> l.getName().equals("고난이도 루트에 도전하는 단계")).findFirst().orElseThrow();
            Level v6 = levelRepository.findAll().stream()
                    .filter(l -> l.getName().equals("어디서든 인정받는 단계")).findFirst().orElseThrow();

            // 더클라임 레벨 체계
            List<GymLevel> theClimbLevels = List.of(
                    GymLevel.builder()
                            .brand(theClimb)
                            .level(v0)
                            .name("ORANGE")
                            .srMin(600)
                            .srMax(649)
                            .sortOrder(1)
                            .imageUrls(List.of(
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342816150_5faf82cd.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342828908_e4ea3ea8.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342842270_7e69777c.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342852399_074c9866.png"
                            ))
                            .build(),
                    GymLevel.builder()
                            .brand(theClimb)
                            .level(v1)
                            .name("GREEN")
                            .srMin(650)
                            .srMax(999)
                            .sortOrder(2)
                            .imageUrls(List.of(
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342767408_19d0bfab.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342783033_cc8b9215.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342794384_08cfbc8d.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342804402_98c6eb82.png"
                            ))
                            .build(),
                    GymLevel.builder()
                            .brand(theClimb)
                            .level(v3)
                            .name("BLUE")
                            .srMin(1000)
                            .srMax(1999)
                            .sortOrder(3)
                            .imageUrls(List.of(
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342705645_f06c1397.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342726345_829842c6.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342740697_0d9ad769.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342752100_186f7d40.png"
                            ))
                            .build(),
                    GymLevel.builder()
                            .brand(theClimb)
                            .level(v4)
                            .name("RED")
                            .srMin(2000)
                            .srMax(2999)
                            .sortOrder(4)
                            .imageUrls(List.of(
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342931077_45be128c.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342943430_3d801d8f.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342954921_90b458ae.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342972102_f6ecbc16.png"
                            ))
                            .build(),
                    GymLevel.builder()
                            .brand(theClimb)
                            .level(v6)
                            .name("PURPLE")
                            .srMin(3000)
                            .srMax(null)
                            .sortOrder(5)
                            .imageUrls(List.of(
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342867309_7cb84915.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342891482_a9b43fee.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342902606_a7d0f7d7.png",
                                    "https://kr.object.ncloudstorage.com/holdy/images/gymlevels/1755342915042_66aadbe0.png"
                            ))
                            .build()
            );

            gymLevelRepository.saveAll(theClimbLevels);
            gymLevelsCreated = theClimbLevels.size();
        }
//
//        // 5. 섹터 데이터 생성
//        if (sectorRepository.count() == 0) {
//            List<Sector> sectors = sectorRepository.saveAll(List.of(
//                    Sector.builder()
//                            .name("A구역")
//                            .imageUrl("https://example.com/sector-a.jpg")
//                            .build(),
//                    Sector.builder()
//                            .name("B구역")
//                            .imageUrl("https://example.com/sector-b.jpg")
//                            .build(),
//                    Sector.builder()
//                            .name("C구역")
//                            .imageUrl("https://example.com/sector-c.jpg")
//                            .build(),
//                    Sector.builder()
//                            .name("D구역")
//                            .imageUrl("https://example.com/sector-d.jpg")
//                            .build()
//            ));
//            sectorsCreated = sectors.size();
//        }
//
//        // 6. 루트 미션 30개 생성
//        if (routeMissionRepository.count() == 0) {
//            List<ClimbingGym> gyms = climbingGymRepository.findAll();
//            List<Sector> sectors = sectorRepository.findAll();
//
//            if (!gyms.isEmpty() && !sectors.isEmpty()) {
//                String[] difficulties = {"ORANGE", "GREEN", "BLUE", "RED", "PURPLE"};
//
//                List<RouteMission> routeMissions = new ArrayList<>();
//                for (int i = 1; i <= 30; i++) {
//                    ClimbingGym gym = gyms.get((i - 1) % gyms.size());
//                    Sector sector = sectors.get((i - 1) % sectors.size());
//                    String difficulty = difficulties[(i - 1) % difficulties.length];
//
//                    routeMissions.add(RouteMission.builder()
//                            .gym(gym)
//                            .sector(sector)
//                            .difficulty(difficulty)
//                            .score(600 + (i - 1) * 50)
//                            .imageUrl("https://example.com/route-mission-" + i + ".jpg")
//                            .thumbnailUrl("https://example.com/route-mission-" + i + "-thumb.jpg")
//                            .videoUrl("https://example.com/route-mission-" + i + ".mp4")
//                            .postedAt(LocalDateTime.now().minusDays(30 - i))
//                            .build());
//                }
//
//                routeMissionRepository.saveAll(routeMissions);
//                routeMissionsCreated = routeMissions.size();
//            }
//        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "테스트 데이터가 생성되었습니다.");
        response.put("brandsCreated", brandsCreated);
        response.put("levelsCreated", levelsCreated);
        response.put("gymsCreated", gymsCreated);
        response.put("gymLevelsCreated", gymLevelsCreated);
//        response.put("sectorsCreated", sectorsCreated);
//        response.put("routeMissionsCreated", routeMissionsCreated);
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
        response.put("sectorCount", sectorRepository.count());
        response.put("routeMissionCount", routeMissionRepository.count());
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
//        long sectorCount = sectorRepository.count();
//        long routeMissionCount = routeMissionRepository.count();

//        routeMissionRepository.deleteAll();
        gymLevelRepository.deleteAll();
        climbingGymRepository.deleteAll();
//        sectorRepository.deleteAll();
        levelRepository.deleteAll();
        brandRepository.deleteAll();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "테스트 데이터가 삭제되었습니다.");
        response.put("deletedBrands", brandCount);
        response.put("deletedLevels", levelCount);
        response.put("deletedGyms", gymCount);
        response.put("deletedGymLevels", gymLevelCount);
//        response.put("deletedSectors", sectorCount);
//        response.put("deletedRouteMissions", routeMissionCount);
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

    @PostMapping("/image")
    public ResponseEntity<ApiResult<Map<String, String>>> testImageUpload(
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam(value = "category", defaultValue = "test") String category
    ) {
        String imageUrl = uploadService.uploadMultipartFile(imageFile, category, "images");

        Map<String, String> result = new HashMap<>();
        result.put("originalFileName", imageFile.getOriginalFilename());
        result.put("uploadedUrl", imageUrl);
        result.put("category", category);
        result.put("fileSize", String.valueOf(imageFile.getSize()));

        return ResponseEntity.ok(ApiResult.success("이미지 업로드 테스트 성공", result));
    }

    @PostMapping("/video")
    public ResponseEntity<ApiResult<Map<String, String>>> testVideoUpload(
            @RequestParam("video") MultipartFile videoFile,
            @RequestParam(value = "category", defaultValue = "test") String category
    ) throws Exception {
        String tempPath = saveTemporaryVideoFile(videoFile);
        String videoUrl = uploadService.uploadVideo(tempPath, category);

        Map<String, String> result = new HashMap<>();
        result.put("originalFileName", videoFile.getOriginalFilename());
        result.put("uploadedUrl", videoUrl);
        result.put("category", category);
        result.put("fileSize", String.valueOf(videoFile.getSize()));

        return ResponseEntity.ok(ApiResult.success("영상 업로드 테스트 성공", result));
    }

    private String saveTemporaryVideoFile(MultipartFile file) throws Exception {
        Path tempDir = Paths.get("temp");
        Files.createDirectories(tempDir);

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path tempFilePath = tempDir.resolve(fileName);

        try (FileOutputStream fos = new FileOutputStream(tempFilePath.toFile())) {
            fos.write(file.getBytes());
        }

        return tempFilePath.toString();
    }

    @GetMapping("/error")
    public String testError() {
        throw new RuntimeException("테스트 에러입니다!");
    }
}