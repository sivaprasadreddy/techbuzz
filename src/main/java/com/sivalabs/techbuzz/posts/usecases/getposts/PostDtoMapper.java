package com.sivalabs.techbuzz.posts.usecases.getposts;

import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.security.SecurityService;
import com.sivalabs.techbuzz.users.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PostDtoMapper {
    private final SecurityService securityService;

    public PostDtoMapper(SecurityService securityService) {
        this.securityService = securityService;
    }

    public List<PostDTO> toDTOs(List<Post> posts) {
        if (posts == null) {
            return new ArrayList<>(0);
        }
        User loginUser = securityService.loginUser();
        return posts.stream().map(post -> this.toDTO(loginUser, post)).toList();
    }

    public PostDTO toDTO(Post post) {
        return toDTO(securityService.loginUser(), post);
    }

    public PostDTO toDTO(User loginUser, Post post) {
        String category = null;
        if (post.getCategory() != null) {
            category = post.getCategory().getName();
        }
        boolean editable = this.canCurrentUserEditPost(loginUser, post);
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getUrl(),
                post.getContent(),
                category,
                post.getCreatedBy().getId(),
                post.getCreatedBy().getName(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                editable);
    }

    public boolean canCurrentUserEditPost(User loginUser, Post post) {
        return loginUser != null
                && post != null
                && (Objects.equals(post.getCreatedBy().getId(), loginUser.getId())
                        || loginUser.isAdminOrModerator());
    }
}
