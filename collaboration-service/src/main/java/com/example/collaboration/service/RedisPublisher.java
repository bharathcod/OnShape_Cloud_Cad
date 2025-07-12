package com.example.collaboration.service;

import com.example.collaboration.model.CollabMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;

    public RedisPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.topic = new ChannelTopic("collab-channel");
    }

    public void publish(CollabMessage message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
