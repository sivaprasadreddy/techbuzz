package com.sivalabs.techbuzz.posts.domain.models;

import static org.assertj.core.api.Assertions.assertThat;

import com.sivalabs.techbuzz.users.domain.models.RoleEnum;
import com.sivalabs.techbuzz.users.domain.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class PostTest {
    @Test
    void shouldNotBeAbleToEditPostByOtherNormalUsers() {
        User otherUser = new User(9L);
        User user = new User(1L);
        user.setRole(RoleEnum.ROLE_USER);
        Post post = new Post();
        post.setId(2L);
        post.setCreatedBy(user);

        assertThat(post.canEditByUser(otherUser)).isFalse();
    }

    @Test
    void shouldBeAbleToEditPostByCreatedUser() {
        User user = new User(1L);
        user.setRole(RoleEnum.ROLE_USER);
        Post post = new Post();
        post.setId(2L);
        post.setCreatedBy(user);

        assertThat(post.canEditByUser(user)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(
            value = RoleEnum.class,
            names = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    void shouldBeAbleToEditPostByOtherAdminOrModeratorUsers(RoleEnum role) {
        User otherUser = new User(9L);
        otherUser.setRole(role);
        User user = new User(1L);
        user.setRole(RoleEnum.ROLE_USER);
        Post post = new Post();
        post.setId(2L);
        post.setCreatedBy(user);

        assertThat(post.canEditByUser(otherUser)).isTrue();
    }
}
