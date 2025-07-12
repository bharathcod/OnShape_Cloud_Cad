package com.example.collaboration.service;

import com.example.collaboration.entity.CollabSession;
import com.example.collaboration.repository.CollabSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CollabService {

    private final CollabSessionRepository repo;

    public CollabService(CollabSessionRepository repo) {
        this.repo = repo;
    }

    public CollabSession join(Long documentId, String username) {
        CollabSession session = new CollabSession();
        session.setDocumentId(documentId);
        session.setUsername(username);
        session.setJoinedAt(LocalDateTime.now());
        return repo.save(session);
    }

    public List<CollabSession> getByDocument(Long documentId) {
        return repo.findByDocumentId(documentId);
    }
}
