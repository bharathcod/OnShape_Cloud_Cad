package com.example.collaboration.controller;

import com.example.collaboration.entity.CollabSession;
import com.example.collaboration.service.CollabService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/collab")
public class CollabController {

    private final CollabService service;

    public CollabController(CollabService service) {
        this.service = service;
    }

    @PostMapping("/{docId}")
    public CollabSession join(@PathVariable Long docId, Principal principal) {
        return service.join(docId, principal.getName());
    }

    @GetMapping("/{docId}")
    public List<CollabSession> get(@PathVariable Long docId) {
        return service.getByDocument(docId);
    }
}
