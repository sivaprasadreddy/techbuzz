package com.sivalabs.techbuzz.posts.usecases.createpost;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreatePostRequest(
        @NotEmpty(message = "Title should not be blank") String title,
        String url,
        @NotEmpty(message = "Content should not be blank") String content,
        @NotNull(message = "CategoryId should not be blank") Long categoryId,
        Long createdUserId) {}
