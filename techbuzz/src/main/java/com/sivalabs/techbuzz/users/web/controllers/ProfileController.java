package com.sivalabs.techbuzz.users.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.users.domain.models.UserProfile;
import com.sivalabs.techbuzz.users.domain.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Loggable
public class ProfileController {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}")
    public String getUserProfile(@PathVariable(name = "userId") Long userId, Model model) {
        log.info("Fetching user profile for {}", userId);
        String userSpecificPostsUrl = "/users/" + userId + "/posts/";
        UserProfile userProfile = userService
                .getUserProfile(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User Id %s not found", userId)));
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("userSpecificPostsUrl", userSpecificPostsUrl);
        return "users/profile";
    }
}
