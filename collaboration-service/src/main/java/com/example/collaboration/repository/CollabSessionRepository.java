package com.example.collaboration.repository;

import com.example.collaboration.entity.CollabSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollabSessionRepository extends JpaRepository<CollabSession, Long> {
    List<CollabSession> findByDocumentId(Long documentId);
}
