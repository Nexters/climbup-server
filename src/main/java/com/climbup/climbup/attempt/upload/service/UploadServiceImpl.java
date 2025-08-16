package com.climbup.climbup.attempt.upload.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.climbup.climbup.attempt.upload.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadServiceImpl implements UploadService {

    private final AmazonS3 amazonS3;

    @Value("${ncp.storage.bucket-name}")
    private String bucketName;

    @Value("${ncp.storage.base-url}")
    private String baseUrl;

    /**
     * 로컬 파일 업로드 (청크 결합 후 사용)
     */
    @Override
    public String uploadFile(String localFilePath, String category, String fileType) {
        try {
            File file = new File(localFilePath);

            if (!file.exists()) {
                throw new IOException("Local file not found: " + localFilePath);
            }

            String extension = FileUtils.getFileExtension(file.getName());
            String fileName = FileUtils.generateNCPFilePath(category, fileType, extension);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.length());
            metadata.setContentType(FileUtils.getContentType(extension));

            try (FileInputStream fis = new FileInputStream(file)) {
                PutObjectRequest putObjectRequest = new PutObjectRequest(
                        bucketName, fileName, fis, metadata
                );

                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
                amazonS3.putObject(putObjectRequest);

                String fileUrl = baseUrl + "/" + fileName;
                log.info("{} uploaded successfully: {}", fileType, fileUrl);

                Files.deleteIfExists(Path.of(localFilePath));

                return fileUrl;
            }
        } catch (Exception e) {
            log.error("Failed to upload {} to NCP storage: {}", fileType, localFilePath, e);
            throw new RuntimeException(fileType + " 업로드에 실패했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * MultipartFile 직접 업로드
     */
    @Override
    public String uploadMultipartFile(MultipartFile file, String category, String fileType) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("업로드할 파일이 비어있습니다.");
            }

            String extension = FileUtils.getFileExtension(file.getOriginalFilename());
            String fileName = FileUtils.generateNCPFilePath(category, fileType, extension);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(FileUtils.getContentType(extension));

            metadata.addUserMetadata("original-filename", file.getOriginalFilename());

            try (InputStream inputStream = file.getInputStream()) {
                PutObjectRequest putObjectRequest = new PutObjectRequest(
                        bucketName, fileName, inputStream, metadata
                );

                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
                amazonS3.putObject(putObjectRequest);

                String fileUrl = baseUrl + "/" + fileName;
                log.info("{} uploaded directly to NCP: {} (original: {})",
                        fileType, fileUrl, file.getOriginalFilename());

                return fileUrl;
            }
        } catch (Exception e) {
            log.error("Failed to upload MultipartFile {} to NCP storage: {}",
                    fileType, file.getOriginalFilename(), e);
            throw new RuntimeException(fileType + " 업로드에 실패했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * 영상 업로드
     */
    @Override
    public String uploadVideo(String localFilePath, String category) {
        return uploadFile(localFilePath, category, "videos");
    }

    /**
     * 이미지 업로드
     */
    @Override
    public String uploadImage(String localFilePath, String category) {
        return uploadFile(localFilePath, category, "images");
    }
}