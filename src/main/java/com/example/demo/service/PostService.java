/*
 * Copyright (c) 2025. demo Spring Boot BE.
 * Created by: Trung Chau
 *
 * This file is part of demo Spring Boot BE.
 */

package com.example.demo.service;

import com.example.demo.dto.request.PostRequest;
import com.example.demo.dto.response.PostResponse;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse createPost(PostRequest request) {
        // 1. Get current logged-in user from SecurityContext
        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        // 2. Find user in DB to ensure they exist
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 3. Create Post Entity
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .build();

        // 4. Save to DB
        Post savedPost = postRepository.save(post);

        // 5. Convert to Response DTO
        return PostResponse.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .createdAt(savedPost.getCreatedAt())
                .authorName(savedPost.getUser().getUsername())
                .build();
    }

    public List<PostResponse> getAllPosts() {
        // 1. Fetch all posts from DB
        List<Post> posts = postRepository.findAll();

        // 2. Map Entity List to DTO List using Stream
        return posts.stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .createdAt(post.getCreatedAt())
                        .authorName(post.getUser().getUsername()) // Get author name from relationship
                        .build())
                .collect(Collectors.toList());
    }
}
