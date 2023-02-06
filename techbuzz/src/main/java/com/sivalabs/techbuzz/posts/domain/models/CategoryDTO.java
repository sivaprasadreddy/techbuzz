package com.sivalabs.techbuzz.posts.domain.models;

import java.time.LocalDateTime;

public record CategoryDTO(
        Long id,
        String name,
        String slug,
        String description,
        String image,
        Integer displayOrder,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {}
