package com.example.plm.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DocumentLifecycle {
    @Id
    private Long documentId;

    private String state;
    private LocalDateTime updatedAt;

    public Long getDocumentId() { return documentId; }
    public void setDocumentId(Long documentId) { this.documentId = documentId; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
