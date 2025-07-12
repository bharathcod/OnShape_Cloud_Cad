package com.example.collaboration.controller;

import com.example.collaboration.model.CollabMessage;
import com.example.collaboration.service.RedisPublisher;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class CollabWebSocketController {

    private RedisPublisher redisPublisher;

    @MessageMapping("/collab.send")
    public void send(CollabMessage message) {
        redisPublisher.publish(message); // broadcast through Redis instead of direct sendTo
    }


}
