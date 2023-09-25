package com.sivalabs.techbuzz.users.domain.dtos;

import com.sivalabs.techbuzz.users.domain.models.RoleEnum;

public record UserDTO(
        Long id,
        String name,
        String email,
        RoleEnum role,
        boolean verified,
        String verificationToken,
        String passwordResetToken) {}
