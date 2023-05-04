package com.sivalabs.techbuzz.posts.mappers;

import com.sivalabs.techbuzz.posts.domain.dtos.PostViewDTO;
import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.models.Vote;
import com.sivalabs.techbuzz.users.domain.dtos.UserDTO;
import com.sivalabs.techbuzz.users.domain.models.User;
import com.sivalabs.techbuzz.users.mappers.UserDTOMapper;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final UserDTOMapper userDTOMapper;

    public PostMapper(final UserDTOMapper userDTOMapper) {
        this.userDTOMapper = userDTOMapper;
    }

    public PostViewDTO toPostViewDTO(User loginUser, Post post) {
        if (post == null) {
            return null;
        }
        Category category = post.getCategory();
        Set<Vote> voteS = post.getVotes();
        UserDTO user = userDTOMapper.toDTO(post.getCreatedBy());
        boolean editable = post.canEditByUser(loginUser);
        boolean upVoted = post.isUpVotedByUser(loginUser);
        boolean downVoted = post.isDownVotedByUser(loginUser);
        return new PostViewDTO(
                post.getId(),
                post.getTitle(),
                post.getUrl(),
                post.getContent(),
                category,
                voteS,
                user,
                post.getCreatedAt(),
                post.getUpdatedAt(),
                editable,
                upVoted,
                downVoted);
    }
}
