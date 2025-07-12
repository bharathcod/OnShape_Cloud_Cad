package com.example.user.service;

import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.user.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User register(String username, String email, String password, Set<String> roles) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        return repo.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public String login(String username, String password) {
        User user = repo.findByUsername(username).orElseThrow();
        if (passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtUtil.generateToken(username, user.getRoles());
            String refresh = UUID.randomUUID().toString();
            user.setRefreshToken(refresh);
            repo.save(user);
            return token;
        }
        throw new RuntimeException("Invalid credentials");
    }

    public String refresh(String refreshToken) {
        User user = repo.findAll().stream()
                .filter(u -> refreshToken.equals(u.getRefreshToken()))
                .findFirst()
                .orElseThrow();
        return jwtUtil.generateToken(user.getUsername(), user.getRoles());
    }
}
