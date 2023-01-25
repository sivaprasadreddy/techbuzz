package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.UnauthorisedAccessException;
import com.sivalabs.techbuzz.config.annotations.AnyAuthenticatedUser;
import com.sivalabs.techbuzz.config.annotations.CurrentUser;
import com.sivalabs.techbuzz.posts.domain.models.PostDTO;
import com.sivalabs.techbuzz.posts.usecases.getposts.GetPostsHandler;
import com.sivalabs.techbuzz.posts.usecases.updatepost.UpdatePostHandler;
import com.sivalabs.techbuzz.posts.usecases.updatepost.UpdatePostRequest;
import com.sivalabs.techbuzz.users.domain.User;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UpdatePostController {

    private static final String MODEL_ATTRIBUTE_POST = "post";

    private final GetPostsHandler getPostsHandler;
    private final UpdatePostHandler updatePostHandler;

    @GetMapping("/posts/{id}/edit")
    @AnyAuthenticatedUser
    public String editPostForm(@PathVariable Long id, @CurrentUser User loginUser, Model model) {
        PostDTO post = getPostsHandler.getPost(id);
        this.checkPrivilege(post, loginUser);
        Long categoryId = null;
        if (post.category() != null) {
            categoryId = post.category().id();
        }
        UpdatePostRequest updatePostRequest =
                new UpdatePostRequest(id, post.title(), post.url(), post.content(), categoryId);

        model.addAttribute(MODEL_ATTRIBUTE_POST, updatePostRequest);
        return "edit-post";
    }

    @PutMapping("/posts/{id}")
    @AnyAuthenticatedUser
    public String updateBookmark(
            @PathVariable Long id,
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_POST) UpdatePostRequest request,
            BindingResult bindingResult,
            @CurrentUser User loginUser,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "edit-post";
        }
        PostDTO post = getPostsHandler.getPost(id);
        var updatePostRequest =
                new UpdatePostRequest(
                        id,
                        request.title(),
                        request.url(),
                        request.content(),
                        request.categoryId());
        this.checkPrivilege(post, loginUser);
        PostDTO updatedPost = updatePostHandler.updatePost(updatePostRequest);
        log.info("Post with id: {} updated successfully", updatedPost.id());
        redirectAttributes.addFlashAttribute("message", "Post updated successfully");
        return "redirect:/posts/" + updatedPost.id() + "/edit";
    }

    private void checkPrivilege(PostDTO post, User loginUser) {
        if (!(Objects.equals(post.createdBy().getId(), loginUser.getId())
                || loginUser.isAdminOrModerator())) {
            throw new UnauthorisedAccessException("Permission Denied");
        }
    }
}
