package com.climbup.climbup.attempt.upload.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class FileUtils {

    public static String generateUniqueFileName(String originalFilename, String extension) {
        return String.format("%s_%s.%s",
                System.currentTimeMillis(),
                UUID.randomUUID().toString().substring(0, 8),
                extension != null ? extension : "tmp"
        );
    }

    public static String generateNCPFilePath(String category, String fileType, String extension) {
        String fileName = generateUniqueFileName(null, extension);
        return String.format("%s/%s/%s", fileType, category, fileName);
    }

    public static String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "mp4";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex == -1 ? "mp4" : filename.substring(lastDotIndex + 1);
    }

    public static String getContentType(String fileExtension) {
        return switch (fileExtension.toLowerCase()) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "webp" -> "image/webp";
            case "gif" -> "image/gif";
            case "mp4" -> "video/mp4";
            case "mov" -> "video/quicktime";
            case "avi" -> "video/x-msvideo";
            default -> "application/octet-stream";
        };
    }
}