package com.amreshmaurya.bookstoreapp.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

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

        //  null / empty check
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is required");
        }

        //  size validation
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("File size must be <= 3MB");
        }

        //  content type validation
        String contentType = file.getContentType();
        if (contentType == null ||
                (!contentType.equals("image/png") &&
                 !contentType.equals("image/jpeg") &&
                 !contentType.equals("image/jpg"))) {
            throw new RuntimeException("Only PNG, JPG, JPEG files are allowed");
        }

        //  safe file name
        String fileName = "books/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        //  put request with metadata
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(contentType) 
                .build();

        s3Client.putObject(
                putRequest,
                RequestBody.fromBytes(file.getBytes())
        );

        
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
    }
}