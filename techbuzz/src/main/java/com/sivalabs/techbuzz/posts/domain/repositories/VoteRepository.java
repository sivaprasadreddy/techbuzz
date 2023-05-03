package com.sivalabs.techbuzz.posts.domain.repositories;

import com.sivalabs.techbuzz.posts.domain.models.Vote;
import java.util.Optional;

public interface VoteRepository {

    Optional<Vote> findByPostIdAndUserId(Long postId, Long userId);

    void deleteVotesForPost(Long postId);

    Vote save(Vote vote);

    void update(Vote vote);
}
