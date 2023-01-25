package com.sivalabs.techbuzz.posts.domain.models;

import com.sivalabs.techbuzz.users.domain.UserDTO;
import java.time.LocalDateTime;
import java.util.Set;

public record PostUserViewDTO(
        Long id,
        String title,
        String url,
        String content,
        CategoryDTO category,
        Set<VoteDTO> votes,
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
        return votes.stream().filter(v -> v.value() == 1).count();
    }

    public long getDownVoteCount() {
        if (votes == null) {
            return 0;
        }
        return votes.stream().filter(v -> v.value() == -1).count();
    }
}
