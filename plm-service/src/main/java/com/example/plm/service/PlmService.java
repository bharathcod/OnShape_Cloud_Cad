package com.example.plm.service;

import com.example.plm.model.DocumentLifecycle;
import com.example.plm.model.EcrRequest;
import com.example.plm.repository.DocumentLifecycleRepository;
import com.example.plm.repository.EcrRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PlmService {
    private final DocumentLifecycleRepository lifecycleRepo;
    private final EcrRepository ecrRepo;
    private final RestTemplate restTemplate;

    @Value("${document-service.url}")
    private String documentServiceUrl;

    @Value("${notification-service.url}")
    private String notificationServiceUrl;

    public PlmService(DocumentLifecycleRepository lifecycleRepo, EcrRepository ecrRepo, RestTemplate restTemplate) {
        this.lifecycleRepo = lifecycleRepo;
        this.ecrRepo = ecrRepo;
        this.restTemplate = restTemplate;
    }

    public DocumentLifecycle releaseDocument(Long documentId, String user) {
        if (!validateDocument(documentId)) throw new RuntimeException("Invalid document");

        DocumentLifecycle state = lifecycleRepo.findById(documentId).orElse(new DocumentLifecycle());
        state.setDocumentId(documentId);
        state.setState("RELEASED");
        state.setUpdatedAt(LocalDateTime.now());
        lifecycleRepo.save(state);

        sendNotification(user, "DOCUMENT_RELEASED", "Document " + documentId + " was released.");
        return state;
    }

    public DocumentLifecycle getLifecycleState(Long documentId) {
        return lifecycleRepo.findById(documentId).orElse(null);
    }

    public EcrRequest createEcr(EcrRequest request) {
        if (!validateDocument(request.getDocumentId())) throw new RuntimeException("Invalid document");

        request.setRequestedAt(LocalDateTime.now());
        EcrRequest saved = ecrRepo.save(request);

        sendNotification(request.getRequestedBy(), "ECR_CREATED", "ECR submitted for document " + request.getDocumentId());
        return saved;
    }

    public List<Map<String, Object>> getMockBOM(Long documentId) {
        return List.of(
                Map.of("partNumber", "P-1001", "name", "Base Plate", "qty", 1),
                Map.of("partNumber", "P-1002", "name", "Support Arm", "qty", 2)
        );
    }

    private boolean validateDocument(Long docId) {
        try {
            restTemplate.getForObject(documentServiceUrl + "/api/document/" + docId, Map.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void sendNotification(String user, String type, String message) {
        try {
            restTemplate.postForObject(notificationServiceUrl + "/api/notify",
                    Map.of("user", user, "type", type, "message", message), Void.class);
        } catch (Exception ignored) {}
    }
}
