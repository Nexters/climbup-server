package com.climbup.climbup.attempt.upload.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String uploadFile(String localFilePath, String category, String fileType);
    String uploadVideo(String localFilePath, String category);
    String uploadImage(String localFilePath, String category);

    // 임시 파일 없이 직접 업로드
    String uploadMultipartFile(MultipartFile file, String category, String fileType);
}