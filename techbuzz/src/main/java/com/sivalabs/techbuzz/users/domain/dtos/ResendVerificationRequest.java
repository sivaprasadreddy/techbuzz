package com.sivalabs.techbuzz.users.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResendVerificationRequest(
        @NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email address") String email) {}
