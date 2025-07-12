package com.example.storage.controller;

import com.example.storage.entity.FileMetadata;
import com.example.storage.service.StorageService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/storage")
public class StorageController {

    private final StorageService service;

    public StorageController(StorageService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public FileMetadata upload(@RequestParam("file") MultipartFile file,
                               @RequestParam("documentId") Long docId) throws Exception {
        return service.uploadFile(file, docId);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) throws Exception {
        byte[] content = service.downloadFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file")
                .body(content);
    }

    @GetMapping("/list/{documentId}")
    public List<FileMetadata> list(@PathVariable Long documentId) {
        return service.listFiles(documentId);
    }
}
