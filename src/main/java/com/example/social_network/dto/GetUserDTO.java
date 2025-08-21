package com.example.social_network.dto;

import com.example.social_network.model.Post;
import com.example.social_network.model.Role;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record GetUserDTO(UUID id, String name, String email, Role role, LocalDateTime createdAt, Set<Post> posts) {
}
