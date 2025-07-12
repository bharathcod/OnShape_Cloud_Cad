package com.example.collaboration.service;

import com.example.collaboration.model.CollabMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisSubscriber {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisSubscriber(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void onMessage(byte[] message) {
        try {
            CollabMessage msg = objectMapper.readValue(message, CollabMessage.class);
            messagingTemplate.convertAndSend("/topic/collab", msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
