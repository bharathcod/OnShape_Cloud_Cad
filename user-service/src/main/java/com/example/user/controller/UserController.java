package com.example.user.controller;

import com.example.user.entity.User;
import com.example.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody Map<String, Object> req) {
        return service.register(
                (String) req.get("username"),
                (String) req.get("email"),
                (String) req.get("password"),
                Set.copyOf((java.util.List<String>) req.get("roles"))
        );
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> req) {
        return service.login(req.get("username"), req.get("password"));
    }

    @PostMapping("/refresh")
    public String refresh(@RequestBody Map<String, String> req) {
        return service.refresh(req.get("refreshToken"));
    }
}
