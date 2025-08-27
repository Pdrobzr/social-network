package com.example.social_network.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePostDTO(@NotBlank String title, @NotBlank String content) {
}
