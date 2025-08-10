package com.climbup.climbup.route.service;

import com.climbup.climbup.attempt.upload.service.ImageService;
import com.climbup.climbup.attempt.upload.service.UploadService;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.gym.exception.GymNotFoundException;
import com.climbup.climbup.gym.repository.ClimbingGymRepository;
import com.climbup.climbup.route.dto.request.CreateRouteMissionRequest;
import com.climbup.climbup.route.dto.request.UpdateRouteMissionRequest;
import com.climbup.climbup.route.dto.response.RouteMissionListResponse;
import com.climbup.climbup.route.dto.response.RouteMissionResponse;
import com.climbup.climbup.route.entity.RouteMission;
import com.climbup.climbup.route.exception.RouteMissionCreateFailedException;
import com.climbup.climbup.route.exception.RouteMissionUpdateFailedException;
import com.climbup.climbup.route.exception.RouteNotFoundException;
import com.climbup.climbup.route.repository.RouteMissionRepository;
import com.climbup.climbup.sector.entity.Sector;
import com.climbup.climbup.sector.exception.SectorNotFoundException;
import com.climbup.climbup.sector.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RouteMissionServiceImpl implements RouteMissionService {

    private final RouteMissionRepository routeMissionRepository;
    private final ClimbingGymRepository climbingGymRepository;
    private final SectorRepository sectorRepository;
    private final UploadService uploadService;
    private final ImageService imageService;

    @Override
    @Transactional
    public RouteMissionResponse createRouteMission(
            CreateRouteMissionRequest request,
            MultipartFile routeImage,
            MultipartFile guideVideo,
            MultipartFile videoThumbnail
    ) {
        try {
            ClimbingGym gym = climbingGymRepository.findById(request.getGymId())
                    .orElseThrow(() -> new GymNotFoundException(request.getGymId()));

            Sector sector = sectorRepository.findById(request.getSectorId())
                    .orElseThrow(() -> new SectorNotFoundException(request.getSectorId()));

            String imageUrl = uploadRouteImage(routeImage);
            String videoUrl = uploadGuideVideo(guideVideo);
            String thumbnailUrl = uploadVideoThumbnail(videoThumbnail);

            RouteMission mission = RouteMission.builder()
                    .gym(gym)
                    .sector(sector)
                    .difficulty(request.getDifficulty())
                    .score(request.getScore())
                    .imageUrl(imageUrl)
                    .videoUrl(videoUrl)
                    .thumbnailUrl(thumbnailUrl)
                    .postedAt(LocalDateTime.now())
                    .build();

            mission = routeMissionRepository.save(mission);

            log.info("Route mission created successfully: {}", mission.getId());
            return RouteMissionResponse.from(mission);

        } catch (Exception e) {
            log.error("Failed to create route mission", e);
            throw new RouteMissionCreateFailedException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RouteMissionResponse getRouteMission(Long missionId) {
        RouteMission mission = routeMissionRepository.findByIdAndRemovedAtIsNull(missionId)
                .orElseThrow(() -> new RouteNotFoundException(missionId));

        return RouteMissionResponse.from(mission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RouteMissionListResponse> getRouteMissions(Long gymId, Long sectorId, String difficulty) {
        List<RouteMission> missions = routeMissionRepository.findRouteMissionsWithFilters(gymId, sectorId, difficulty);
        return missions.stream()
                .map(RouteMissionListResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public RouteMissionResponse updateRouteMission(
            Long missionId,
            UpdateRouteMissionRequest request,
            MultipartFile routeImage,
            MultipartFile guideVideo,
            MultipartFile videoThumbnail
    ) {
        try {
            RouteMission mission = routeMissionRepository.findByIdAndRemovedAtIsNull(missionId)
                    .orElseThrow(() -> new RouteNotFoundException(missionId));

            if (request.getGymId() != null) {
                ClimbingGym gym = climbingGymRepository.findById(request.getGymId())
                        .orElseThrow(() -> new GymNotFoundException(request.getGymId()));
                mission.setGym(gym);
            }

            if (request.getSectorId() != null) {
                Sector sector = sectorRepository.findById(request.getSectorId())
                        .orElseThrow(() -> new SectorNotFoundException(request.getSectorId()));
                mission.setSector(sector);
            }

            if (request.getDifficulty() != null) {
                mission.setDifficulty(request.getDifficulty());
            }

            if (request.getScore() != null) {
                mission.setScore(request.getScore());
            }

            // 파일 업로드 (제공된 경우에만)
            if (routeImage != null && !routeImage.isEmpty()) {
                String imageUrl = uploadRouteImage(routeImage);
                mission.setImageUrl(imageUrl);
            }

            if (guideVideo != null && !guideVideo.isEmpty()) {
                String videoUrl = uploadGuideVideo(guideVideo);
                mission.setVideoUrl(videoUrl);
            }

            if (videoThumbnail != null && !videoThumbnail.isEmpty()) {
                String thumbnailUrl = uploadVideoThumbnail(videoThumbnail);
                mission.setThumbnailUrl(thumbnailUrl);
            }

            mission = routeMissionRepository.save(mission);

            log.info("Route mission updated successfully: {}", mission.getId());
            return RouteMissionResponse.from(mission);

        } catch (Exception e) {
            log.error("Failed to update route mission: {}", missionId, e);
            throw new RouteMissionUpdateFailedException(missionId, e);
        }
    }

    @Override
    @Transactional
    public void deleteRouteMission(Long missionId) {
        RouteMission mission = routeMissionRepository.findByIdAndRemovedAtIsNull(missionId)
                .orElseThrow(() -> new RouteNotFoundException(missionId));

        mission.setRemovedAt(LocalDateTime.now());
        routeMissionRepository.save(mission);

        log.info("Route mission soft deleted: {}", missionId);
    }

    private String uploadRouteImage(MultipartFile routeImage) {
        String tempImagePath = imageService.saveTemporaryImageFile(routeImage);
        return uploadService.uploadImage(tempImagePath, "routes");
    }

    private String uploadGuideVideo(MultipartFile guideVideo) {
        String tempVideoPath = saveTemporaryFile(guideVideo);
        return uploadService.uploadVideo(tempVideoPath, "guides");
    }

    private String uploadVideoThumbnail(MultipartFile videoThumbnail) {
        String tempThumbnailPath = imageService.saveTemporaryImageFile(videoThumbnail);
        return uploadService.uploadImage(tempThumbnailPath, "guides");
    }

    private String saveTemporaryFile(MultipartFile file) {
        try {
            Path tempDir = Paths.get("temp");
            Files.createDirectories(tempDir);

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path tempFilePath = tempDir.resolve(fileName);

            try (FileOutputStream fos = new FileOutputStream(tempFilePath.toFile())) {
                fos.write(file.getBytes());
            }

            return tempFilePath.toString();

        } catch (IOException e) {
            log.error("Failed to save temporary file: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("임시 파일 저장에 실패했습니다.", e);
        }
    }
}