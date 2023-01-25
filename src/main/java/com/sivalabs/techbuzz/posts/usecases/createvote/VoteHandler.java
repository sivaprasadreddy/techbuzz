package com.sivalabs.techbuzz.posts.usecases.createvote;

import com.sivalabs.techbuzz.posts.domain.entities.Vote;
import com.sivalabs.techbuzz.posts.domain.models.VoteDTO;
import com.sivalabs.techbuzz.posts.domain.repositories.VoteRepository;
import com.sivalabs.techbuzz.posts.mappers.VoteDTOMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VoteHandler {

    private final VoteRepository voteRepository;
    private final VoteDTOMapper voteDTOMapper;

    public VoteDTO addVote(CreateVoteRequest request) {
        Optional<Vote> voteOptional =
                voteRepository.findByPostIdAndUserId(request.postId(), request.userId());
        if (voteOptional.isEmpty()) {
            Vote vote =
                    new Vote(
                            null,
                            request.userId(),
                            request.postId(),
                            request.value(),
                            LocalDateTime.now(),
                            null);
            Vote savedVote = voteRepository.save(vote);
            log.info("Vote saved successfully");
            return voteDTOMapper.toDTO(savedVote);
        }
        Vote existingVote = voteOptional.get();
        existingVote.setValue(request.value());
        voteRepository.save(existingVote);
        log.info("Vote update successfully");
        return voteDTOMapper.toDTO(existingVote);
    }
}
