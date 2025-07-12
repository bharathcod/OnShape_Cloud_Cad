package com.example.comment.service;

import com.example.comment.entity.Comment;
import com.example.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository repo;

    public CommentService(CommentRepository repo) {
        this.repo = repo;
    }

    public Comment addComment(Long docId, String username, String content) {
        Comment comment = new Comment();
        comment.setDocumentId(docId);
        comment.setUsername(username);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        return repo.save(comment);
    }

    public List<Comment> getCommentsByDocument(Long docId) {
        return repo.findByDocumentId(docId);
    }
}
