package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.config.annotations.AnyAuthenticatedUser;
import com.sivalabs.techbuzz.config.annotations.CurrentUser;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostHandler;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostRequest;
import com.sivalabs.techbuzz.users.domain.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreatePostController {
    private static final Logger logger = LoggerFactory.getLogger(CreatePostController.class);
    private static final String MODEL_ATTRIBUTE_POST = "post";

    private final CreatePostHandler createPostHandler;

    public CreatePostController(CreatePostHandler createPostHandler) {
        this.createPostHandler = createPostHandler;
    }

    @GetMapping("/posts/new")
    @AnyAuthenticatedUser
    public String newPostForm(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_POST, new CreatePostRequest("", "","", null, null));
        return "add-post";
    }

    @PostMapping("/posts")
    @AnyAuthenticatedUser
    public String createPost(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_POST) CreatePostRequest request,
            BindingResult bindingResult,
            @CurrentUser User loginUser) {
        if (bindingResult.hasErrors()) {
            return "add-post";
        }
        var createPostRequest =
                new CreatePostRequest(
                        request.title(), request.url(), request.content(), request.categoryId(), loginUser.getId());
        Post post = createPostHandler.createPost(createPostRequest);
        logger.info("Post saved successfully with id: {}", post.getId());
        return "redirect:/c/"+post.getCategory().getName();
    }
}
