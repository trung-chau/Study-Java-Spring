/*
 * Copyright (c) 2025. demo Spring Boot BE.
 * Created by: Trung Chau
 *
 * This file is part of demo Spring Boot BE.
 */

package com.example.demo.controller;

import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.service.AuthenticationService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final AuthenticationService service;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUsersByUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.loadUserByUsername(username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable String id,
            @RequestBody RegisterRequest req
    ) {
        return ResponseEntity.ok(service.updateUser(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable @Min(value = 1, message = "Minium input value is 1") Long id) {
        service.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
