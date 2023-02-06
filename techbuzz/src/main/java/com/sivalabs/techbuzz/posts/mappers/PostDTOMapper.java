package com.sivalabs.techbuzz.posts.mappers;

import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.models.CategoryDTO;
import com.sivalabs.techbuzz.posts.domain.models.PostDTO;
import com.sivalabs.techbuzz.posts.domain.models.PostUserViewDTO;
import com.sivalabs.techbuzz.posts.domain.models.VoteDTO;
import com.sivalabs.techbuzz.users.domain.User;
import com.sivalabs.techbuzz.users.domain.UserDTO;
import com.sivalabs.techbuzz.users.mappers.UserDTOMapper;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostDTOMapper {
    private final CategoryDTOMapper categoryDTOMapper;
    private final VoteDTOMapper voteDTOMapper;
    private final UserDTOMapper userDTOMapper;

    public PostDTO toDTO(Post post) {
        CategoryDTO category = categoryDTOMapper.toDTO(post.getCategory());
        Set<VoteDTO> voteDTOS = voteDTOMapper.toDTOs(post.getVotes());
        UserDTO user = userDTOMapper.toDTO(post.getCreatedBy());
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getUrl(),
                post.getContent(),
                category,
                voteDTOS,
                user,
                post.getCreatedAt(),
                post.getUpdatedAt());
    }

    public PostUserViewDTO toPostUserViewDTO(User loginUser, Post post) {
        CategoryDTO category = categoryDTOMapper.toDTO(post.getCategory());
        Set<VoteDTO> voteDTOS = voteDTOMapper.toDTOs(post.getVotes());
        UserDTO user = userDTOMapper.toDTO(post.getCreatedBy());
        boolean editable = this.canCurrentUserEditPost(loginUser, post);
        boolean upVoted = isVotedByUser(post, loginUser, 1);
        boolean downVoted = isVotedByUser(post, loginUser, -1);

        return new PostUserViewDTO(
                post.getId(),
                post.getTitle(),
                post.getUrl(),
                post.getContent(),
                category,
                voteDTOS,
                user,
                post.getCreatedAt(),
                post.getUpdatedAt(),
                editable,
                upVoted,
                downVoted);
    }

    private boolean isVotedByUser(Post post, User loginUser, int vote) {
        if (loginUser == null || post == null || post.getVotes() == null) {
            return false;
        }
        return post.getVotes().stream()
                .anyMatch(
                        v ->
                                Objects.equals(v.getUserId(), loginUser.getId())
                                        && v.getValue() == vote);
    }

    public boolean canCurrentUserEditPost(User loginUser, Post post) {
        return loginUser != null
                && post != null
                && (Objects.equals(post.getCreatedBy().getId(), loginUser.getId())
                        || loginUser.isAdminOrModerator());
    }
}
