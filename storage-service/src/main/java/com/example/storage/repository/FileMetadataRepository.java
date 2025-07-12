package com.example.storage.repository;

import com.example.storage.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    List<FileMetadata> findByDocumentId(Long documentId);
}
