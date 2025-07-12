package com.example.storage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class FileMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String s3Key;
    private String fileType;
    private Long documentId;
    private LocalDateTime uploadedAt;

    // Getters and setters
}
