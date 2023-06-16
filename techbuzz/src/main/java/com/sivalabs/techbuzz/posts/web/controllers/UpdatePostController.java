package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.UnauthorisedAccessException;
import com.sivalabs.techbuzz.config.annotations.AnyAuthenticatedUser;
import com.sivalabs.techbuzz.config.annotations.CurrentUser;
import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.posts.domain.dtos.UpdatePostRequest;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.services.PostService;
import com.sivalabs.techbuzz.users.domain.models.User;
import jakarta.validation.Valid;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Loggable
public class UpdatePostController {
    private static final Logger log = LoggerFactory.getLogger(UpdatePostController.class);

    private static final String MODEL_ATTRIBUTE_POST = "post";
    private final PostService postService;

    public UpdatePostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/{id}/edit")
    @AnyAuthenticatedUser
    public String editPostForm(@PathVariable Long id, @CurrentUser User loginUser, Model model) {
        log.info("Edit post form requested for Post ID: {} by User ID: {}", id, loginUser.getId());
        Post post = postService.getPost(id);
        checkPrivilege(post, loginUser);
        Long categoryId = post.getCategory().getId();
        UpdatePostRequest updatePostRequest =
                new UpdatePostRequest(id, post.getTitle(), post.getUrl(), post.getContent(), categoryId);
        model.addAttribute(MODEL_ATTRIBUTE_POST, updatePostRequest);
        model.addAttribute("categorySlug", post.getCategory().getSlug());
        return "posts/edit-post";
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
            return "posts/edit-post";
        }
        Post post = postService.getPost(id);
        var updatePostRequest =
                new UpdatePostRequest(id, request.title(), request.url(), request.content(), request.categoryId());
        checkPrivilege(post, loginUser);
        Post updatedPost = postService.updatePost(updatePostRequest);
        log.info("Post with id: {} updated successfully", updatedPost.getId());
        redirectAttributes.addFlashAttribute("message", "Post updated successfully");
        return "redirect:/posts/" + updatedPost.getId() + "/edit";
    }

    private static void checkPrivilege(Post post, User loginUser) {
        if (!(Objects.equals(post.getCreatedBy().getId(), loginUser.getId()) || loginUser.isAdminOrModerator())) {
            throw new UnauthorisedAccessException("Permission Denied");
        }
    }
}
