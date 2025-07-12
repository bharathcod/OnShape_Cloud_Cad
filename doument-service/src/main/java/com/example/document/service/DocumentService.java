package com.example.document.service;

import com.example.document.entity.Document;
import com.example.document.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DocumentService {

    private final DocumentRepository repo;
    private final RestTemplate restTemplate;

    public DocumentService(DocumentRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }

    public Document createDocument(String name, String owner, String content) {
        Document doc = new Document();
        doc.setName(name);
        doc.setOwner(owner);
        doc.setContent(content);
        doc.setCreatedAt(LocalDateTime.now());
        Document saved = repo.save(doc);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = new HashMap<>();
            body.put("documentId", saved.getId().toString());
            body.put("versionLabel", "v1");
            body.put("content", content);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity("http://localhost:8083/api/versions", request, Void.class);
        } catch (Exception e) {
            System.err.println("Failed to notify versioning-service: " + e.getMessage());
        }

        return saved;
    }

    public List<Document> getAllDocuments() {
        return repo.findAll();
    }

    public Optional<Document> getDocumentById(Long id) {
        return repo.findById(id);
    }

    public void deleteDocument(Long id) {
        repo.deleteById(id);
    }

    public Document updateDocument(Document doc) {
        return repo.save(doc);
    }

}
