package com.sivalabs.techbuzz.users.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.users.domain.dtos.PasswordResetRequest;
import com.sivalabs.techbuzz.users.domain.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Loggable
public class PasswordResetController {
    private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);
    private static final String CHANGE_PASSWORD = "users/resetPassword";

    private final UserService userService;

    public PasswordResetController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/reset-password")
    public String passwordResetForm(
            Model model,
            @RequestParam("email") String email,
            @RequestParam("token") String token,
            RedirectAttributes redirectAttributes) {
        try {
            logger.info("Loading Reset Password form");
            userService.verifyPasswordResetToken(email, token);
            PasswordResetRequest passwordResetRequest = new PasswordResetRequest(email, token, "");
            model.addAttribute("resetPassword", passwordResetRequest);
            return CHANGE_PASSWORD;
        } catch (ResourceNotFoundException e) {
            logger.error("Error during updating password: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Password reset  failed, please try again");
            return "redirect:/reset-password";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @ModelAttribute("resetPassword") @Valid PasswordResetRequest passwordResetRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return CHANGE_PASSWORD;
        }
        try {
            userService.changePassword(passwordResetRequest);
            redirectAttributes.addFlashAttribute("message", "Password reset is successful");
            return "redirect:/login";

        } catch (RuntimeException e) {
            logger.error("Error during updating password: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Password reset  failed, please try again");
            return "redirect:/reset-password";
        }
    }
}
