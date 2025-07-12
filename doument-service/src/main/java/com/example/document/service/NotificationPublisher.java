package com.example.document.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    public NotificationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(String username, String type, String message) {
        Map<String, String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("type", type);
        payload.put("message", message);

        rabbitTemplate.convertAndSend("cad.notifications", payload);
    }
}
