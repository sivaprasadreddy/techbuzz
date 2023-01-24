package com.sivalabs.techbuzz.posts.usecases.getposts;

import java.time.LocalDateTime;
import java.util.Set;

public record PostDTO(
        Long id,
        String title,
        String url,
        String content,
        String category,
        Set<VoteDTO> votes,
        Long createdUserId,
        String createdUserName,
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
