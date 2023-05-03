package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.UnauthorisedAccessException;
import com.sivalabs.techbuzz.config.annotations.AnyAuthenticatedUser;
import com.sivalabs.techbuzz.config.annotations.CurrentUser;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.usecases.deletepost.DeletePostHandler;
import com.sivalabs.techbuzz.posts.usecases.getposts.GetPostsHandler;
import com.sivalabs.techbuzz.users.domain.models.User;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class DeletePostController {
    private final GetPostsHandler getPostsHandler;
    private final DeletePostHandler deletePostHandler;

    public DeletePostController(final GetPostsHandler getPostsHandler, final DeletePostHandler deletePostHandler) {
        this.getPostsHandler = getPostsHandler;
        this.deletePostHandler = deletePostHandler;
    }

    @DeleteMapping("/posts/{id}")
    @ResponseStatus
    @AnyAuthenticatedUser
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @CurrentUser User loginUser) {
        Post post = getPostsHandler.getPost(id);
        this.checkPrivilege(post, loginUser);
        deletePostHandler.deletePost(id);
        return ResponseEntity.ok().build();
    }

    private void checkPrivilege(Post post, User loginUser) {
        if (!(Objects.equals(post.getCreatedBy().getId(), loginUser.getId()) || loginUser.isAdminOrModerator())) {
            throw new UnauthorisedAccessException("Permission Denied");
        }
    }
}
