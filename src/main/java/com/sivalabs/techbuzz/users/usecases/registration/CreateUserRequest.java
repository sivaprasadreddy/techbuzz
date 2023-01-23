package com.sivalabs.techbuzz.users.usecases.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "Name cannot be blank") String name,
        @NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email address")
                String email,
        @NotBlank(message = "Password cannot be blank") String password) {}
