/*
 * Copyright (c) 2025. demo Spring Boot BE.
 * Created by: Trung Chau
 *
 * This file is part of demo Spring Boot BE.
 */

package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @GetMapping("/normal")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello! This page is for everyone who is logged in.");
    }

    @GetMapping("/admin-only")
    @PreAuthorize("hasAuthority('ADMIN')") 
    public ResponseEntity<String> sayHelloAdmin() {
        return ResponseEntity.ok("Hello Boss! Only the Admin can see this message.");
    }
}
