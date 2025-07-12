package com.example.notification.controller;

import com.example.notification.entity.Notification;
import com.example.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Notification> get(Principal principal) {
        return service.getForUser(principal.getName());
    }
}
