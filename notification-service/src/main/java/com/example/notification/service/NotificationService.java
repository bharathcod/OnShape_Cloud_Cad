package com.example.notification.service;

import com.example.notification.entity.Notification;
import com.example.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public void save(String username, String type, String message) {
        Notification n = new Notification();
        n.setUsername(username);
        n.setType(type);
        n.setMessage(message);
        n.setCreatedAt(LocalDateTime.now());
        repo.save(n);
    }

    public List<Notification> getForUser(String username) {
        return repo.findByUsername(username);
    }
}
