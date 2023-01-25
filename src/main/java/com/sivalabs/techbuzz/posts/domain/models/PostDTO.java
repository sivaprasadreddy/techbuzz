package com.sivalabs.techbuzz.posts.domain.models;

import com.sivalabs.techbuzz.users.domain.UserDTO;
import java.time.LocalDateTime;
import java.util.Set;

public record PostDTO(
        Long id,
        String title,
        String url,
        String content,
        CategoryDTO category,
        Set<VoteDTO> votes,
        UserDTO createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {}
