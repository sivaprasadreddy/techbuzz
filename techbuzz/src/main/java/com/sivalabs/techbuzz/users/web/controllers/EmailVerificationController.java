package com.sivalabs.techbuzz.users.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.TechBuzzException;
import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.users.domain.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Loggable
class EmailVerificationController {
    private final UserService userService;

    public EmailVerificationController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/verify-email")
    public String verifyEmail(Model model, @RequestParam("email") String email, @RequestParam("token") String token) {
        try {
            userService.verifyEmail(email, token);
            model.addAttribute("success", true);
        } catch (TechBuzzException e) {
            model.addAttribute("success", false);
        }
        return "users/emailVerification";
    }
}
