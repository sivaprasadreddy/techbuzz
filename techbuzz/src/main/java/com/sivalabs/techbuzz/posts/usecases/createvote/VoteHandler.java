package com.sivalabs.techbuzz.posts.usecases.createvote;

import com.sivalabs.techbuzz.posts.domain.models.Vote;
import com.sivalabs.techbuzz.posts.domain.repositories.VoteRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VoteHandler {
    private static final Logger log = LoggerFactory.getLogger(VoteHandler.class);

    private final VoteRepository voteRepository;

    public VoteHandler(final VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote addVote(CreateVoteRequest request) {
        log.debug("Adding vote :{} for postId: {} by userId:{}", request.value(), request.postId(), request.userId());
        Optional<Vote> voteOptional = voteRepository.findByPostIdAndUserId(request.postId(), request.userId());
        if (voteOptional.isEmpty()) {
            Vote vote = new Vote(null, request.userId(), request.postId(), request.value(), LocalDateTime.now(), null);
            Vote savedVote = voteRepository.save(vote);
            log.info("Vote saved successfully");
            return savedVote;
        }
        Vote existingVote = voteOptional.get();
        existingVote.setValue(request.value());
        voteRepository.update(existingVote);
        log.info("Vote update successfully");
        return existingVote;
    }
}
