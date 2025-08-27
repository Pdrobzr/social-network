package com.example.social_network.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetPostsByUserDTO(UUID postId, String title, String content, LocalDateTime createdAt) {
}
