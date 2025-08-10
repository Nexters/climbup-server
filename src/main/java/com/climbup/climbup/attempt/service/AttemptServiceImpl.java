package com.climbup.climbup.attempt.service;

import com.climbup.climbup.attempt.dto.LevelSRReward;
import com.climbup.climbup.attempt.dto.request.CreateAttemptRequest;
import com.climbup.climbup.attempt.dto.response.CreateAttemptResponse;
import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import com.climbup.climbup.attempt.exception.*;
import com.climbup.climbup.attempt.repository.UserMissionAttemptRepository;
import com.climbup.climbup.attempt.upload.dto.request.RouteMissionUploadChunkRequest;
import com.climbup.climbup.attempt.upload.dto.request.RouteMissionUploadSessionInitializeRequest;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadChunkResponse;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadSessionFinalizeResponse;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadSessionInitializeResponse;
import com.climbup.climbup.attempt.upload.dto.response.RouteMissionUploadStatusResponse;
import com.climbup.climbup.attempt.upload.entity.UploadSession;
import com.climbup.climbup.attempt.upload.entity.Chunk;
import com.climbup.climbup.attempt.upload.enums.UploadStatus;
import com.climbup.climbup.attempt.upload.exception.VideoUploadFailedException;
import com.climbup.climbup.attempt.upload.repository.UploadSessionRepository;
import com.climbup.climbup.attempt.upload.repository.ChunkRepository;
import com.climbup.climbup.attempt.upload.service.ImageService;
import com.climbup.climbup.attempt.upload.service.UploadService;
import com.climbup.climbup.common.exception.CommonBusinessException;
import com.climbup.climbup.common.exception.ErrorCode;
import com.climbup.climbup.common.exception.ValidationException;
import com.climbup.climbup.route.entity.RouteMission;
import com.climbup.climbup.route.exception.RouteNotFoundException;
import com.climbup.climbup.route.repository.RouteMissionRepository;
import com.climbup.climbup.session.entity.UserSession;
import com.climbup.climbup.session.exception.UserSessionNotFoundException;
import com.climbup.climbup.session.repository.UserSessionRepository;
import com.climbup.climbup.sr.entity.SRHistory;
import com.climbup.climbup.sr.repository.SRHistoryRepository;
import com.climbup.climbup.user.entity.User;
import com.climbup.climbup.user.exception.UserNotFoundException;
import com.climbup.climbup.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AttemptServiceImpl implements AttemptService {

    private final UserMissionAttemptRepository attemptRepository;
    private final RouteMissionRepository routeMissionRepository;
    private final UserRepository userRepository;
    private final UserSessionRepository sessionRepository;
    private final SRHistoryRepository srHistoryRepository;
    private final UploadSessionRepository uploadSessionRepository;
    private final ChunkRepository chunkRepository;
    private final UploadService uploadService;
    private final ImageService imageService;


    private Path getUploadSessionDirectory(UUID uploadId) {
        return Paths.get("uploads", uploadId.toString(), "chunks");
    }

    private void ensureDirectoryExists(Path uploadDirectory) {
        try {
            Files.createDirectories(uploadDirectory);
        } catch (IOException e) {
            throw new CommonBusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Path getChunkFilePath(UUID uploadId, int chunkIndex) {
        return Paths.get("uploads", uploadId.toString(), "chunks", String.valueOf(chunkIndex));
    }

    private String combineChunks(UploadSession uploadSession)
    {
        String fileName = uploadSession.getFileName() + "." + uploadSession.getFileType();
        Path finalFilePath = Paths.get("uploads", uploadSession.getId().toString(), fileName);
        ensureDirectoryExists(finalFilePath.getParent());

        List<Chunk> sortedChunks = uploadSession.getChunks().stream()
                .sorted(Comparator.comparing(Chunk::getChunkIndex))
                .toList();

        try (FileOutputStream fos = new FileOutputStream(finalFilePath.toFile());
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {

            for (Chunk chunk : sortedChunks) {
                Path chunkPath = Paths.get(chunk.getFilePath());
                byte[] chunkData = Files.readAllBytes(chunkPath);
                bos.write(chunkData);
            }
            bos.flush();
        } catch (IOException e) {
            log.error(e.toString());
            throw new CommonBusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        for (Chunk chunk : sortedChunks) {
            try {
                Files.deleteIfExists(Paths.get(chunk.getFilePath()));
            } catch (IOException e) {
                log.warn("Failed to delete chunk file: {}", chunk.getFilePath(), e);
            }
        }

        return finalFilePath.toString();
    }


    @Transactional
    public CreateAttemptResponse createAttempt(Long userId, CreateAttemptRequest request) {
        log.info("Creating attempt for user: {}, mission: {}, success: {}", userId, request.getMissionId(), request.getSuccess());

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        RouteMission mission = routeMissionRepository.findById(request.getMissionId())
                .orElseThrow(RouteNotFoundException::new);

        UserSession activeSession = sessionRepository.findByUserIdAndEndedAtIsNull(userId)
                .orElseThrow(UserSessionNotFoundException::new);

        UserMissionAttempt attempt = UserMissionAttempt.builder()
                .user(user)
                .session(activeSession)
                .mission(mission)
                .success(request.getSuccess())
                .build();

        attempt = attemptRepository.save(attempt);

        Integer srGained = 0;
        Integer currentSr = user.getSr();

        if (request.getSuccess()) {
            try {
                LevelSRReward reward = LevelSRReward.fromLevelName(mission.getDifficulty());
                srGained = reward.getSrReward();

                Integer srBefore = user.getSr();
                Integer srAfter = srBefore + srGained;

                user.updateSr(srAfter);
                currentSr = srAfter;

                SRHistory srHistory = SRHistory.builder()
                        .user(user)
                        .session(activeSession)
                        .mission(mission)
                        .srBefore(srBefore)
                        .srAfter(srAfter)
                        .build();

                srHistoryRepository.save(srHistory);

                log.info("SR updated for user: {} from {} to {} (+{}) for difficulty: {}",
                        userId, srBefore, srAfter, srGained, mission.getDifficulty());
            } catch (IllegalArgumentException e) {
                log.warn("Invalid difficulty level: {} for mission: {}", mission.getDifficulty(), mission.getId());
                throw new ValidationException(ErrorCode.INVALID_DIFFICULTY_LEVEL, mission.getDifficulty());
            }
        }

        return CreateAttemptResponse.builder()
                .missionAttemptId(attempt.getId())
                .success(attempt.getSuccess())
                .videoUrl(attempt.getVideoUrl())
                .createdAt(attempt.getCreatedAt())
                .srGained(srGained)
                .currentSr(currentSr)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public RouteMissionUploadStatusResponse getAttemptUploadStatus(Long attemptId) {
        UserMissionAttempt attempt = attemptRepository.findById(attemptId).orElseThrow(AttemptNotFoundException::new);

        var uploadSession = attempt.getUpload();

        if(uploadSession == null) {
            throw new UploadSessionNotFoundException();
        }

        return RouteMissionUploadStatusResponse.toDto(uploadSession);
    }

    @Override
    @Transactional
    public RouteMissionUploadSessionInitializeResponse initializeAttemptUploadSession(Long attemptId, RouteMissionUploadSessionInitializeRequest request) {
        UserMissionAttempt attempt = attemptRepository.findById(attemptId).orElseThrow(AttemptNotFoundException::new);

        if(attempt.getUpload() != null) {
            throw new UploadSessionAlreadyExistsException();
        }

        UploadSession uploadSession = UploadSession.builder().status(UploadStatus.NOT_STARTED).chunkLength(request.getChunkLength()).chunkSize(request.getChunkSize()).fileSize(request.getFileSize()).fileName(request.getFileName()).fileType(request.getFileType()).build();

        uploadSession = uploadSessionRepository.save(uploadSession);

        attempt.setUpload(uploadSession);

        attemptRepository.save(attempt);

        return RouteMissionUploadSessionInitializeResponse.builder().uploadId(uploadSession.getId()).build();
    }

    @Override
    @Transactional
    public RouteMissionUploadChunkResponse uploadChunk(UUID uploadId, RouteMissionUploadChunkRequest request) {

        UploadSession uploadSession = uploadSessionRepository.findById(uploadId).orElseThrow(UploadSessionNotFoundException::new);

        if(uploadSession.hasChunk(request.getIndex())) {
            throw new UploadSessionChunkAlreadyExistsException();
        }

        try {
            Path sessionDir = getUploadSessionDirectory(uploadId);
            ensureDirectoryExists(sessionDir);

            Path chunkFilePath = getChunkFilePath(uploadId, request.getIndex());
            Files.write(chunkFilePath, request.getChunk());

            Chunk chunk = new Chunk();
            chunk.setChunkIndex(request.getIndex());
            chunk.setChunkSize((long) request.getChunk().length);
            chunk.setCompleted(true);
            chunk.setFilePath(chunkFilePath.toString());

            uploadSession.addChunk(chunk);

            String chunkName = String.format("chunk_%d_%s", request.getIndex(), uploadSession.getFileName());
            chunk.setName(chunkName);

            chunkRepository.save(chunk);


        } catch (IOException e) {
            log.error("Failed to store chunk {} for upload session {}", request.getIndex(), uploadId, e);
            throw new UploadSessionChunkIncompleteException();
        }

        if (uploadSession.getStatus() == UploadStatus.NOT_STARTED) {
            uploadSession.setStatus(UploadStatus.IN_PROGRESS);
            uploadSessionRepository.save(uploadSession);
        }


        return RouteMissionUploadChunkResponse.builder()
                .index(request.getIndex())
                .totalChunkReceived(uploadSession.getReceivedChunkCount().intValue())
                .totalChunkExpected(uploadSession.getChunkLength())
                .build();
    }

    @Override
    @Transactional
    public RouteMissionUploadSessionFinalizeResponse finalizeUploadSession(UUID uploadId, MultipartFile thumbnailFile) {
        UploadSession uploadSession = uploadSessionRepository.findById(uploadId)
                .orElseThrow(UploadSessionNotFoundException::new);

        long receivedChunks = uploadSession.getReceivedChunkCount();
        int expectedChunks = uploadSession.getChunkLength();

        if (receivedChunks != expectedChunks) {
            throw new UploadSessionChunkIncompleteException();
        }

        String finalVideoPath = combineChunks(uploadSession);

        try {
            String videoUrl = uploadService.uploadVideo(finalVideoPath, "attempts");
            log.info("Video uploaded to NCP successfully. Video URL: {}", videoUrl);

            String thumbnailUrl = null;
            boolean thumbnailUploaded = false;

            if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
                String tempImagePath = imageService.saveTemporaryImageFile(thumbnailFile);
                thumbnailUrl = uploadService.uploadImage(tempImagePath, "attempts");
                thumbnailUploaded = true;
                log.info("Thumbnail uploaded to NCP successfully. Thumbnail URL: {}", thumbnailUrl);
            }

            UserMissionAttempt attempt = attemptRepository.findByUploadId(uploadId)
                    .orElseThrow(AttemptNotFoundException::new);

            attempt.setVideoUrl(videoUrl);
            if (thumbnailUrl != null) {
                attempt.setThumbnailUrl(thumbnailUrl);
            }
            attemptRepository.save(attempt);

            uploadSession.setStatus(UploadStatus.FINISHED);
            uploadSessionRepository.save(uploadSession);

            log.info("Upload session finalized successfully for attempt: {}. Video URL: {}, Thumbnail URL: {}",
                    attempt.getId(), videoUrl, thumbnailUrl);

            cleanupLocalFiles(finalVideoPath, uploadSession);

            return RouteMissionUploadSessionFinalizeResponse.builder()
                    .fileName(uploadSession.getFileName())
                    .videoUrl(videoUrl)
                    .thumbnailUrl(thumbnailUrl)
                    .thumbnailUploaded(thumbnailUploaded)
                    .build();

        } catch (Exception e) {
            log.error("Failed to finalize upload session: {}", uploadId, e);
            throw new VideoUploadFailedException(e);
        }
    }

    private void cleanupLocalFiles(String finalVideoPath, UploadSession uploadSession) {
        try {
            Files.deleteIfExists(Paths.get(finalVideoPath));

            Path uploadDir = Paths.get("uploads", uploadSession.getId().toString());

            if (Files.exists(uploadDir)) {
                try (Stream<Path> pathStream = Files.walk(uploadDir)) {
                    pathStream
                            .sorted(Comparator.reverseOrder())
                            .map(Path::toFile)
                            .forEach(file -> {
                                try {
                                    Files.delete(file.toPath());
                                } catch (IOException e) {
                                    log.warn("Failed to delete file: {}", file.getPath(), e);
                                }
                            });
                }
            }

            log.info("Local temporary files cleaned up for upload session: {}", uploadSession.getId());
        } catch (Exception e) {
            log.warn("Failed to cleanup local files for upload session: {}", uploadSession.getId(), e);
        }
    }
}