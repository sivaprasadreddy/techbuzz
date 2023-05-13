package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.config.annotations.AnyAuthenticatedUser;
import com.sivalabs.techbuzz.config.annotations.CurrentUser;
import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.posts.domain.dtos.CreatePostRequest;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.services.PostService;
import com.sivalabs.techbuzz.users.domain.models.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Loggable
public class CreatePostController {
    private static final Logger log = LoggerFactory.getLogger(CreatePostController.class);

    private static final String MODEL_ATTRIBUTE_POST = "post";
    private final PostService postService;

    public CreatePostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/new")
    @AnyAuthenticatedUser
    public String newPostForm(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_POST, new CreatePostRequest("", "", "", null, null));
        return "posts/add-post";
    }

    @PostMapping("/posts")
    @AnyAuthenticatedUser
    public String createPost(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_POST) CreatePostRequest request,
            BindingResult bindingResult,
            @CurrentUser User loginUser,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "posts/add-post";
        }
        var createPostRequest = new CreatePostRequest(
                request.title(), request.url(), request.content(), request.categoryId(), loginUser.getId());
        Post post = postService.createPost(createPostRequest);
        log.info("Post saved successfully with id: {}", post.getId());
        redirectAttributes.addFlashAttribute("message", "Post saved successfully");
        return "redirect:/c/" + post.getCategory().getSlug();
    }
}
