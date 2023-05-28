package com.sivalabs.techbuzz.users.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.TechBuzzException;
import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.users.domain.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Loggable
class EmailVerificationController {

    private static final Logger logger = LoggerFactory.getLogger(EmailVerificationController.class);
    private final UserService userService;

    public EmailVerificationController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/verify-email")
    public String verifyEmail(Model model, @RequestParam("email") String email, @RequestParam("token") String token) {
        logger.info("Verifying email {}", email);
        try {
            userService.verifyEmail(email, token);
            model.addAttribute("success", true);
            logger.info("Email verification successful for email: {}", email);
        } catch (TechBuzzException e) {
            model.addAttribute("success", false);
            logger.error("Email verification failed for email: {}. Error: {}", email, e.getMessage());
        }
        return "users/emailVerification";
    }
}
