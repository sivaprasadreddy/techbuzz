package com.sivalabs.techbuzz.posts.domain.repositories;

import static com.sivalabs.techbuzz.jooq.tables.Votes.VOTES;

import com.sivalabs.techbuzz.jooq.tables.records.VotesRecord;
import com.sivalabs.techbuzz.posts.domain.entities.Vote;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

@Repository
class JooqVoteRepository implements VoteRepository {
    private final DSLContext dsl;

    JooqVoteRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Optional<Vote> findByPostIdAndUserId(Long postId, Long userId) {
        return this.dsl
                .selectFrom(VOTES)
                .where(VOTES.POST_ID.eq(postId).and(VOTES.USER_ID.eq(userId)))
                .fetchOptional(mapToVote());
    }

    @Override
    public void deleteVotesForPost(Long postId) {
        this.dsl.deleteFrom(VOTES).where(VOTES.POST_ID.eq(postId)).execute();
    }

    @Override
    public Vote save(Vote vote) {
        return this.dsl
                .insertInto(VOTES)
                .set(VOTES.USER_ID, vote.getUserId())
                .set(VOTES.POST_ID, vote.getPostId())
                .set(VOTES.VAL, new BigDecimal(vote.getValue()))
                .set(VOTES.CREATED_AT, LocalDateTime.now())
                .returning(VOTES.ID)
                .fetchSingle(mapToVote());
    }

    private static RecordMapper<VotesRecord, Vote> mapToVote() {
        return r -> new Vote(
                r.getId(),
                r.getUserId(),
                r.getPostId(),
                r.getVal() != null ? r.getVal().intValue() : null,
                r.getCreatedAt(),
                r.getUpdatedAt());
    }
}
