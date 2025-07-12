package com.example.plm.controller;

import com.example.plm.model.DocumentLifecycle;
import com.example.plm.model.EcrRequest;
import com.example.plm.service.PlmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plm")
public class PlmController {

    private final PlmService plmService;

    public PlmController(PlmService plmService) {
        this.plmService = plmService;
    }

    @PostMapping("/release")
    public ResponseEntity<DocumentLifecycle> release(@RequestParam Long documentId,
                                                     @RequestParam String user) {
        return ResponseEntity.ok(plmService.releaseDocument(documentId, user));
    }

    @GetMapping("/state/{documentId}")
    public ResponseEntity<DocumentLifecycle> getState(@PathVariable Long documentId) {
        DocumentLifecycle state = plmService.getLifecycleState(documentId);
        return state != null ? ResponseEntity.ok(state) : ResponseEntity.notFound().build();
    }

    @PostMapping("/ecr")
    public ResponseEntity<EcrRequest> createEcr(@RequestBody EcrRequest request) {
        return ResponseEntity.ok(plmService.createEcr(request));
    }

    @GetMapping("/bom/{documentId}")
    public ResponseEntity<List<Map<String, Object>>> getBOM(@PathVariable Long documentId) {
        return ResponseEntity.ok(plmService.getMockBOM(documentId));
    }
}
