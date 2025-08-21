package com.example.social_network.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetPostsDTO(UUID postId, String content, LocalDateTime createdAt, UUID userId) {
}
