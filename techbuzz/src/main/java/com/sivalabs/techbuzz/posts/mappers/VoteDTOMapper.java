package com.sivalabs.techbuzz.posts.mappers;

import com.sivalabs.techbuzz.posts.domain.entities.Vote;
import com.sivalabs.techbuzz.posts.domain.models.VoteDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class VoteDTOMapper {

    public Set<VoteDTO> toDTOs(Set<Vote> votes) {
        if (votes != null) {
            return votes.stream().map(this::toDTO).collect(Collectors.toSet());
        }
        return Set.of();
    }

    public VoteDTO toDTO(Vote vote) {
        return new VoteDTO(vote.getId(), vote.getUserId(), vote.getPostId(), vote.getValue());
    }
}
