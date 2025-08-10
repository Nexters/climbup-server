package com.climbup.climbup.attempt.upload.service;

public interface UploadService {

    String uploadVideo(String localFilePath, String category);

    String uploadImage(String localImagePath, String category);
}
