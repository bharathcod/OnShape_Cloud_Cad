package com.example.comment.controller;

import com.example.comment.entity.Comment;
import com.example.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/{docId}")
    public ResponseEntity<Comment> add(@PathVariable Long docId, @RequestBody Map<String, String> body, Principal principal) {
        return ResponseEntity.ok(service.addComment(docId, principal.getName(), body.get("content")));
    }

    @GetMapping("/{docId}")
    public List<Comment> get(@PathVariable Long docId) {
        return service.getCommentsByDocument(docId);
    }
}
