package com.example.cadapi.service;

import com.example.cadapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CadEngine {
    private final Map<Long, CadModel> modelStore = new HashMap<>();

    @Autowired
    private RestTemplate restTemplate;

    @Value("${versioning-service.url}")
    private String versioningUrl;

    @Value("${document-service.url}")
    private String documentServiceUrl;




    public CadModel createSketch(SketchRequest request) {
        String description = String.format("Sketch: %s [%.2fx%.2f]",
                request.getShapeType(), request.getWidth(), request.getHeight());

        CadModel model = new CadModel("sketch", description);
        modelStore.put(model.getId(), model);

        // 📣 VERSIONING CALL (add this)
        Map<String, Object> payload = Map.of(
                "documentId", request.getDocumentId(),
                "operationType", "SKETCH",
                "description", description
        );
        restTemplate.postForObject(versioningUrl + "/api/version", payload, Void.class);

        Map<String, Object> historyPayload = Map.of(
                "documentId", request.getDocumentId(),
                "action", "Sketch",
                "detail", description,
                "cadModelId", model.getId()
        );

        restTemplate.postForObject(documentServiceUrl + "/api/document/history", historyPayload, Void.class);


        return model;
    }


    public CadModel extrude(ExtrudeRequest request) {
        CadModel base = modelStore.get(request.getSketchId());
        if (base == null || !base.getOperation().equals("sketch")) {
            throw new IllegalArgumentException("Invalid sketch ID");
        }
        String description = base.getData() + String.format(" + Extrude[%.2f]", request.getDepth());
        CadModel model = new CadModel("extrude", description);
        modelStore.put(model.getId(), model);

        // After creating model
        Map<String, Object> payload = Map.of(
                "documentId", base.getId(),
                "operationType", "EXTRUDE",
                "description", description
        );
        restTemplate.postForObject(versioningUrl + "/api/version", payload, Void.class);

        Map<String, Object> historyPayload = Map.of(
                "documentId", request.getId(),
                "action", "Sketch",
                "detail", description,
                "cadModelId", model.getId()
        );

        restTemplate.postForObject(documentServiceUrl + "/api/document/history", historyPayload, Void.class);


        return model;
    }

    public CadModel fillet(FilletRequest request) {
        CadModel base = modelStore.get(request.getModelId());
        if (base == null) {
            throw new IllegalArgumentException("Invalid model ID");
        }
        String description = base.getData() + String.format(" + Fillet[%.2f]", request.getRadius());
        CadModel model = new CadModel("fillet", description);
        modelStore.put(model.getId(), model);

        // After creating model
        Map<String, Object> payload = Map.of(
                "documentId", base.getId(),
                "operationType", "FILLET",
                "description", description
        );
        restTemplate.postForObject(versioningUrl + "/api/version", payload, Void.class);

        Map<String, Object> historyPayload = Map.of(
                "documentId", request.getId(),
                "action", "Sketch",
                "detail", description,
                "cadModelId", model.getId()
        );

        restTemplate.postForObject(documentServiceUrl + "/api/document/history", historyPayload, Void.class);



        return model;
    }

    public CadModel getModel(Long id) {
        return modelStore.get(id);
    }
}
