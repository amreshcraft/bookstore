package com.amreshmaurya.bookstoreapp.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;




@Service
public class S3FileService {

    private final S3Client s3Client;

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    private static final long MAX_FILE_SIZE = 3 * 1024 * 1024; // 3MB

    public S3FileService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) throws IOException {

        validateImage(file);

        String originalName = file.getOriginalFilename();
        String fileName = generateFileName(originalName);

        String contentType = resolveContentType(originalName);

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(contentType)
                .build();

        s3Client.putObject(
                putRequest,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        return buildFileUrl(fileName);
    }

    // ---------------- VALIDATION ----------------

    private void validateImage(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is required");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("File size must be <= 3MB");
        }

        // real validation (content-based)
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            throw new RuntimeException("Invalid image file");
        }
    }

    // ---------------- FILE NAME ----------------

    private String generateFileName(String originalName) {

        String extension = getExtension(originalName);

        return "books/" + UUID.randomUUID() + "." + extension;
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "jpg"; // safe fallback
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }


    private String resolveContentType(String filename) {

        String ext = getExtension(filename).toLowerCase();

        return switch (ext) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "bmp" -> "image/bmp";
            case "webp" -> "image/webp";
            default -> "image/jpeg"; // fallback
        };
    }


    private String buildFileUrl(String key) {
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;
    }


    public void deleteFileFromS3Bucket(String url) {

        if (url == null || url.isBlank()) {
            return;
        }

        try {
            URI uri = new URI(url);
            String key = uri.getPath().substring(1);

            s3Client.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build()
            );

        } catch (Exception e) {
            throw new RuntimeException("Invalid S3 URL");
        }
    }
}