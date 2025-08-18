package com.example.social_network.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserDTO(@NotBlank @Email String email, @NotBlank String password) {
}
