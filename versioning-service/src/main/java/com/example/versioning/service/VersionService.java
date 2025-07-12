package com.example.versioning.service;

import com.example.versioning.entity.Version;
import com.example.versioning.repository.VersionRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class VersionService {

    private final VersionRepository repo;
    private final RestTemplate restTemplate;

    public VersionService(VersionRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }

    public Version create(Long docId, String label, String content) {
        Version version = new Version();
        version.setDocumentId(docId);
        version.setVersionLabel(label);
        version.setContent(content);
        version.setCreatedAt(LocalDateTime.now());
        return repo.save(version);
    }

    public List<Version> getAll(Long docId) {
        return repo.findByDocumentId(docId);
    }

    public Version getById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public boolean restoreVersion(Long versionId) {
        Version version = getById(versionId);

        Map<String, String> payload = new HashMap<>();
        payload.put("content", version.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {
            restTemplate.put("http://localhost:8082/api/documents/" + version.getDocumentId() + "/restore", request);
            return true;
        } catch (Exception e) {
            System.err.println("Error restoring version: " + e.getMessage());
            return false;
        }
    }
}
