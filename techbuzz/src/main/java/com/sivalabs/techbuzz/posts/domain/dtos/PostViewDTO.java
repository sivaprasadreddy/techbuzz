package com.sivalabs.techbuzz.posts.domain.dtos;

import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.models.Vote;
import com.sivalabs.techbuzz.users.domain.dtos.UserDTO;
import java.time.LocalDateTime;
import java.util.Set;

public record PostViewDTO(
        Long id,
        String title,
        String url,
        String content,
        Category category,
        Set<Vote> votes,
        UserDTO createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean editable,
        boolean upVoted,
        boolean downVoted) {

    public long getUpVoteCount() {
        if (votes == null) {
            return 0;
        }
        return votes.stream().filter(v -> v.getValue() == 1).count();
    }

    public long getDownVoteCount() {
        if (votes == null) {
            return 0;
        }
        return votes.stream().filter(v -> v.getValue() == -1).count();
    }
}
