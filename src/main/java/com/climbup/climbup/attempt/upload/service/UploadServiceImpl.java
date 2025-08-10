package com.climbup.climbup.attempt.upload.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadServiceImpl implements UploadService {

    private final AmazonS3 amazonS3;

    @Value("${ncp.storage.bucket-name}")
    private String bucketName;

    @Value("${ncp.storage.base-url}")
    private String baseUrl;

    @Override
    public String uploadVideo(String localFilePath, String category) {
        try {
            File file = new File(localFilePath);
            String fileName = generateVideoFileName(category);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.length());
            metadata.setContentType("video/mp4");

            try (FileInputStream fis = new FileInputStream(file)) {
                PutObjectRequest putObjectRequest = new PutObjectRequest(
                        bucketName,
                        fileName,
                        fis,
                        metadata
                );

                amazonS3.putObject(putObjectRequest);

                String videoUrl = baseUrl + "/" + fileName;
                log.info("Video uploaded successfully: {}", videoUrl);

                return videoUrl;
            }
        } catch (Exception e) {
            log.error("Failed to upload video to NCP storage: {}", localFilePath, e);
            throw new RuntimeException("영상 업로드에 실패했습니다.", e);
        }
    }

    @Override
    public String uploadImage(String localImagePath, String category) {
        try {
            File file = new File(localImagePath);
            String fileName = generateImageFileName(category);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.length());

            String fileExtension = getFileExtension(file.getName()).toLowerCase();
            String contentType = switch (fileExtension) {
                case "jpg", "jpeg" -> "image/jpeg";
                case "png" -> "image/png";
                case "webp" -> "image/webp";
                default -> "image/jpeg";
            };
            metadata.setContentType(contentType);

            try (FileInputStream fis = new FileInputStream(file)) {
                PutObjectRequest putObjectRequest = new PutObjectRequest(
                        bucketName,
                        fileName,
                        fis,
                        metadata
                );

                amazonS3.putObject(putObjectRequest);

                String imageUrl = baseUrl + "/" + fileName;
                log.info("Image uploaded successfully: {}", imageUrl);

                Files.deleteIfExists(Path.of(localImagePath));

                return imageUrl;
            }
        } catch (Exception e) {
            log.error("Failed to upload image to NCP storage: {}", localImagePath, e);
            throw new RuntimeException("이미지 업로드에 실패했습니다.", e);
        }
    }

    private String generateVideoFileName(String category) {
        return String.format("videos/%s/%s_%s.mp4",
                category,
                System.currentTimeMillis(),
                UUID.randomUUID().toString().substring(0, 8)
        );
    }

    private String generateImageFileName(String category) {
        return String.format("images/%s/%s_%s.jpg",
                category,
                System.currentTimeMillis(),
                UUID.randomUUID().toString().substring(0, 8)
        );
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1);
    }
}