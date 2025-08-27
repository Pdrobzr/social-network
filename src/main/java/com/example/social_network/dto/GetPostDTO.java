package com.example.social_network.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetPostDTO(UUID postId, String title, String content, LocalDateTime createdAt, UUID userId) {
}
