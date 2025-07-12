package com.example.plm.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class EcrRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long documentId;
    private String reason;
    private String requestedBy;
    private LocalDateTime requestedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDocumentId() { return documentId; }
    public void setDocumentId(Long documentId) { this.documentId = documentId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getRequestedBy() { return requestedBy; }
    public void setRequestedBy(String requestedBy) { this.requestedBy = requestedBy; }

    public LocalDateTime getRequestedAt() { return requestedAt; }
    public void setRequestedAt(LocalDateTime requestedAt) { this.requestedAt = requestedAt; }
}
