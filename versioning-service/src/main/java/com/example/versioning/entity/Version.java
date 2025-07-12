package com.example.versioning.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long documentId;
    private String versionLabel;
    private LocalDateTime createdAt;

    @Lob
    private String content;

    // Getters and Setters
}
