package com.example.collaboration.config;

import com.example.collaboration.security.JwtUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.List;
import java.util.Map;

@Component
public class WebSocketAuthInterceptor implements HandshakeInterceptor, ChannelInterceptor {

    private final JwtUtil jwtUtil;

    public WebSocketAuthInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes
    ) {
        // Allow handshake
        return true;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        List<String> auth = accessor.getNativeHeader("Authorization");
        if (auth != null && !auth.isEmpty()) {
            String token = auth.get(0).replace("Bearer ", "");
            if (jwtUtil.validateToken(token)) {
                accessor.setUser(() -> jwtUtil.extractUsername(token));
            }
        }
        return message;
    }
}
