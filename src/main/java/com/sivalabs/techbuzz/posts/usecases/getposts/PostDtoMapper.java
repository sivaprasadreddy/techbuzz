package com.sivalabs.techbuzz.posts.usecases.getposts;

import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.entities.Vote;
import com.sivalabs.techbuzz.users.domain.User;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostDtoMapper {

    public PostDTO toDTO(User loginUser, Post post) {
        String category = null;
        if (post.getCategory() != null) {
            category = post.getCategory().getName();
        }
        boolean editable = this.canCurrentUserEditPost(loginUser, post);

        boolean upVoted = false;
        boolean downVoted = false;

        Set<VoteDTO> voteDTOS = Set.of();
        Set<Vote> votes = post.getVotes();
        if (votes != null) {
            voteDTOS =
                    votes.stream()
                            .map(
                                    v ->
                                            new VoteDTO(
                                                    v.getId(),
                                                    v.getUserId(),
                                                    v.getPostId(),
                                                    v.getValue()))
                            .collect(Collectors.toSet());

            if (loginUser != null) {
                upVoted =
                        votes.stream()
                                .anyMatch(
                                        v ->
                                                Objects.equals(v.getUserId(), loginUser.getId())
                                                        && v.getValue() == 1);
                downVoted =
                        votes.stream()
                                .anyMatch(
                                        v ->
                                                Objects.equals(v.getUserId(), loginUser.getId())
                                                        && v.getValue() == -1);
            }
        }
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getUrl(),
                post.getContent(),
                category,
                voteDTOS,
                post.getCreatedBy().getId(),
                post.getCreatedBy().getName(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                editable,
                upVoted,
                downVoted);
    }

    public boolean canCurrentUserEditPost(User loginUser, Post post) {
        return loginUser != null
                && post != null
                && (Objects.equals(post.getCreatedBy().getId(), loginUser.getId())
                        || loginUser.isAdminOrModerator());
    }
}
