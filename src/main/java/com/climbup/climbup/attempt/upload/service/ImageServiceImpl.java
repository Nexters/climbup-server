package com.climbup.climbup.attempt.upload.service;

import com.climbup.climbup.attempt.upload.exception.ImageSaveFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Override
    public String saveTemporaryImageFile(MultipartFile imageFile) {
        try {
            Path tempDir = Paths.get("temp");
            Files.createDirectories(tempDir);

            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path tempFilePath = tempDir.resolve(fileName);

            try (FileOutputStream fos = new FileOutputStream(tempFilePath.toFile())) {
                fos.write(imageFile.getBytes());
            }

            log.info("Temporary image file saved: {}", tempFilePath);
            return tempFilePath.toString();

        } catch (IOException e) {
            log.error("Failed to save temporary image file: {}", imageFile.getOriginalFilename(), e);
            throw new ImageSaveFailedException(imageFile.getOriginalFilename(), e);
        }
    }
}