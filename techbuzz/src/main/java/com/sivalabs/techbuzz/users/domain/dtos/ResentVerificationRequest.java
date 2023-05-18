package com.sivalabs.techbuzz.users.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResentVerificationRequest(
        @NotBlank(message = "Reset verification Email cannot be blank") @Email(message = "Invalid email address")
                String email) {}