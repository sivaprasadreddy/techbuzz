package com.sivalabs.techbuzz.posts.usecases.getposts;

public record VoteDTO(Long id, Long userId, Long postId, Integer value) {
}
