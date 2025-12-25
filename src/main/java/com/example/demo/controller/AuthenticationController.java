/*
 * Copyright (c) 2025. demo Spring Boot BE.
 * Created by: Trung Chau
 *
 * This file is part of demo Spring Boot BE.
 */

package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import com.example.demo.dto.request.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    // API Register
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    // API Login
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        AuthenticationResponse authResponse = service.refreshToken(request, response);
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
    }

    @GetMapping("/test")
    public ResponseEntity<List<User>> test() {
        return ResponseEntity.ok(service.getAllUsers());
    }
}
