package com.example.versioning.controller;

import com.example.versioning.entity.Version;
import com.example.versioning.service.VersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/versions")
public class VersionController {

    private final VersionService service;

    public VersionController(VersionService service) {
        this.service = service;
    }

    @PostMapping
    public Version create(@RequestBody Map<String, String> body) {
        Long docId = Long.parseLong(body.get("documentId"));
        return service.create(docId, body.get("versionLabel"), body.get("content"));
    }

    @GetMapping("/{docId}")
    public List<Version> getAll(@PathVariable Long docId) {
        return service.getAll(docId);
    }

    @GetMapping("/detail/{versionId}")
    public Version getById(@PathVariable Long versionId) {
        return service.getById(versionId);
    }

    @PostMapping("/{versionId}/restore")
    public ResponseEntity<String> restore(@PathVariable Long versionId) {
        boolean success = service.restoreVersion(versionId);
        if (success) {
            return ResponseEntity.ok("Restored version " + versionId);
        } else {
            return ResponseEntity.status(500).body("Failed to restore version.");
        }
    }

}
