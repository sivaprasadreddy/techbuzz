package com.sivalabs.techbuzz.posts.domain.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateVoteRequest(@NotNull Long postId, Long userId, @NotNull Integer value) {}
