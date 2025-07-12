package com.example.document.controller;

import com.example.document.entity.Document;
import com.example.document.service.DocumentService;
import com.example.document.service.NotificationPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService service;
    private final NotificationPublisher notificationPublisher;

    public DocumentController(DocumentService service,NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Document> create(@RequestBody Map<String, String> req) {
        Document doc = service.createDocument(req.get("name"), req.get("owner"), req.get("content"));
        notificationPublisher.sendNotification(
                req.get("name"),
                "DOCUMENT_CREATED",
                "Document '" + doc.getName() + "' created."
        );
        return ResponseEntity.ok(doc);
    }

    @GetMapping
    public List<Document> all() {
        return service.getAllDocuments();
    }

    @GetMapping("/{id}")
    public Document get(@PathVariable Long id) {
        return service.getDocumentById(id).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteDocument(id);
    }

    @PutMapping("/{id}/restore")
    public Document restore(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Document doc = service.getDocumentById(id).orElseThrow();
        doc.setContent(body.get("content"));
        return service.updateDocument(doc);
    }

}
