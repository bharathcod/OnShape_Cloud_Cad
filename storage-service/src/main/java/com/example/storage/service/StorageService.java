package com.example.storage.service;

import com.example.storage.entity.FileMetadata;
import com.example.storage.repository.FileMetadataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class StorageService {

    private final S3Client s3Client;
    private final FileMetadataRepository repo;

    private final String bucket = "cad-files";

    public StorageService(S3Client s3Client, FileMetadataRepository repo) {
        this.s3Client = s3Client;
        this.repo = repo;
    }

    public FileMetadata uploadFile(MultipartFile file, Long documentId) throws IOException {
        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();

        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(file.getContentType())
                .build(), RequestBody.fromBytes(file.getBytes()));

        FileMetadata meta = new FileMetadata();
        meta.setFileName(file.getOriginalFilename());
        meta.setS3Key(key);
        meta.setFileType(file.getContentType());
        meta.setUploadedAt(LocalDateTime.now());
        meta.setDocumentId(documentId);

        return repo.save(meta);
    }

    public byte[] downloadFile(Long id) throws IOException {
        FileMetadata meta = repo.findById(id).orElseThrow();
        GetObjectResponse response = s3Client.getObject(
                GetObjectRequest.builder().bucket(bucket).key(meta.getS3Key()).build(),
                software.amazon.awssdk.core.sync.ResponseTransformer.toBytes()
        );
        return response.asByteArray();
    }

    public List<FileMetadata> listFiles(Long documentId) {
        return repo.findByDocumentId(documentId);
    }
}
