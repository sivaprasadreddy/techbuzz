package com.sivalabs.techbuzz.posts.usecases.updatepost;

import jakarta.validation.constraints.NotEmpty;

public record UpdatePostRequest(
        Long id,
        @NotEmpty(message = "Title should not be blank")
        String title,
        String url,
        @NotEmpty(message = "Content should not be blank")
        String content,
        Long categoryId) {
}
