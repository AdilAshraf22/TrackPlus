package com.trackplus.trackplusbackend.controller;

import com.trackplus.trackplusbackend.dto.LoginRequest;
import com.trackplus.trackplusbackend.dto.RegisterRequest;
import com.trackplus.trackplusbackend.entity.User;
import com.trackplus.trackplusbackend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}
