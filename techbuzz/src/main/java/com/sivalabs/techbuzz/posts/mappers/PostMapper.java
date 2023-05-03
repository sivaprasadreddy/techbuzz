package com.sivalabs.techbuzz.posts.mappers;

import com.sivalabs.techbuzz.posts.domain.dtos.PostUserViewDTO;
import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.models.Vote;
import com.sivalabs.techbuzz.users.domain.dtos.UserDTO;
import com.sivalabs.techbuzz.users.domain.models.User;
import com.sivalabs.techbuzz.users.mappers.UserDTOMapper;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final UserDTOMapper userDTOMapper;

    public PostMapper(final UserDTOMapper userDTOMapper) {
        this.userDTOMapper = userDTOMapper;
    }

    public PostUserViewDTO toPostUserViewDTO(User loginUser, Post post) {
        Category category = post.getCategory();
        Set<Vote> voteDTOS = post.getVotes();
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
                .anyMatch(v -> Objects.equals(v.getUserId(), loginUser.getId()) && v.getValue() == vote);
    }

    public boolean canCurrentUserEditPost(User loginUser, Post post) {
        return loginUser != null
                && post != null
                && (Objects.equals(post.getCreatedBy().getId(), loginUser.getId()) || loginUser.isAdminOrModerator());
    }
}
