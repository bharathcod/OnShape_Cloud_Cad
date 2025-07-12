package com.example.notification.rabbitmq;

import com.example.notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationListener {

    private final NotificationService service;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationListener(NotificationService service, SimpMessagingTemplate messagingTemplate) {
        this.service = service;
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = "cad.notifications")
    public void handleEvent(Map<String, String> event) {
        String username = event.get("username");
        String type = event.get("type");
        String message = event.get("message");

        service.save(username, type, message);

        // 🔔 Push via WebSocket to user
        messagingTemplate.convertAndSend("/topic/notify/" + username, event);
    }
}
