package com.climbup.climbup.attempt.upload.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String saveTemporaryImageFile(MultipartFile imageFile);
}
