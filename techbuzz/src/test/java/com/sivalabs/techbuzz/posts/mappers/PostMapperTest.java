package com.sivalabs.techbuzz.posts.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sivalabs.techbuzz.posts.domain.dtos.PostViewDTO;
import com.sivalabs.techbuzz.posts.domain.mappers.PostMapper;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.models.Vote;
import com.sivalabs.techbuzz.users.domain.mappers.UserDTOMapper;
import com.sivalabs.techbuzz.users.domain.models.User;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostMapperTest {
    UserDTOMapper userDTOMapper;
    PostMapper postMapper;

    @BeforeEach
    void setUp() {
        userDTOMapper = new UserDTOMapper();
        postMapper = new PostMapper(userDTOMapper);
    }

    @Test
    void shouldNotBeDeterminedAsVotedIfUserNotLoggedIn() {
        User user = new User(1L);
        Post post = new Post();
        post.setCreatedBy(user);
        PostViewDTO postViewDTO = postMapper.toPostViewDTO(null, post);
        assertThat(postViewDTO.downVoted()).isFalse();
        assertThat(postViewDTO.upVoted()).isFalse();
    }

    @Test
    void shouldDeterminePostIsNotVotedCorrectly() {
        User user = new User(1L);
        Post post = new Post();
        post.setCreatedBy(user);
        PostViewDTO postViewDTO = postMapper.toPostViewDTO(user, post);
        assertThat(postViewDTO.downVoted()).isFalse();
    }

    @Test
    void shouldDeterminePostIsUpVotedCorrectly() {
        User user = new User(1L);
        Post post = new Post();
        post.setId(2L);
        post.setCreatedBy(user);
        post.setVotes(Set.of(new Vote(null, user.getId(), post.getId(), 1, null, null)));
        PostViewDTO postViewDTO = postMapper.toPostViewDTO(user, post);
        assertThat(postViewDTO.upVoted()).isTrue();
    }

    @Test
    void shouldDeterminePostIsDownVotedCorrectly() {
        User user = new User(1L);
        Post post = new Post();
        post.setId(2L);
        post.setCreatedBy(user);
        post.setVotes(Set.of(new Vote(null, user.getId(), post.getId(), -1, null, null)));
        PostViewDTO postViewDTO = postMapper.toPostViewDTO(user, post);
        assertThat(postViewDTO.downVoted()).isTrue();
    }
}
