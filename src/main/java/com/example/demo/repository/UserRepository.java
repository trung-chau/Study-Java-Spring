/*
 * Copyright (c) 2025. demo Spring Boot BE.
 * Created by: Trung Chau
 *
 * This file is part of demo Spring Boot BE.
 */

package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
