package com.example.versioning.repository;

import com.example.versioning.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VersionRepository extends JpaRepository<Version, Long> {
    List<Version> findByDocumentId(Long documentId);
}
