/*
 * Copyright (c) 2025. demo Spring Boot BE.
 * Created by: Trung Chau
 *
 * This file is part of demo Spring Boot BE.
 */

package com.example.demo.service;

import com.example.demo.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        // 1. Check header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        // 2. Get token
        jwt = authHeader.substring(7);

        // 3. Find token
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);

        // 4. If it exists -> Mark as expired and withdrawn
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            
            // 5. Delete Current SecurityContext
            SecurityContextHolder.clearContext();
        }
    }
}
