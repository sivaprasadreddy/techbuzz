package com.sivalabs.techbuzz.users.web.controllers;

import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.users.domain.models.UserProfile;
import com.sivalabs.techbuzz.users.domain.services.UserService;
import java.util.Optional;
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
        Optional<UserProfile> userProfile = userService.getUserProfile(userId);
        if (userProfile.isEmpty()) {
            return "posts/category";
        }
        model.addAttribute("userProfile", userProfile.get());
        model.addAttribute("userSpecificPostsUrl", userSpecificPostsUrl);
        return "users/profile";
    }
}
