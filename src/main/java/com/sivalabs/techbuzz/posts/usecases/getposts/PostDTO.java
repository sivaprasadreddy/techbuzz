package com.sivalabs.techbuzz.posts.usecases.getposts;

import java.time.LocalDateTime;

public record PostDTO(
        Long id,
        String title,
        String url,
        String content,
        String category,
        Long createdUserId,
        String createdUserName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean editable) {}
