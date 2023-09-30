package com.sivalabs.techbuzz.users.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.users.domain.dtos.ForgotPasswordRequest;
import com.sivalabs.techbuzz.users.domain.dtos.UserDTO;
import com.sivalabs.techbuzz.users.domain.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@Loggable
public class ForgotPasswordController {
    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

    private final UserService userService;
    private static final String FORGOT_PASSWORD = "users/forgotPassword";

    public ForgotPasswordController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordForm(Model model) {
        logger.info("Loading Forgot Password Initiation");
        model.addAttribute("forgotPassword", new ForgotPasswordRequest(""));
        return FORGOT_PASSWORD;
    }

    @PostMapping("/forgot-password")
    public String initiatePasswordReset(
            HttpServletRequest request,
            @Valid @ModelAttribute("forgotPassword") ForgotPasswordRequest forgotPasswordRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        logger.info("Email in password {}", forgotPasswordRequest.email());
        if (bindingResult.hasErrors()) {
            return FORGOT_PASSWORD;
        }

        try {
            UserDTO userDTO = userService.createPasswordResetToken(forgotPasswordRequest);
            this.sendResetPasswordMail(request, userDTO);
            logger.info("Sent password reset link to {}", forgotPasswordRequest.email());
            redirectAttributes.addFlashAttribute("message", "Password reset link is sent to your email");
            return "redirect:/forgot-password";

        } catch (ResourceNotFoundException e) {
            logger.error("Error during sending password reset request error: {}", e.getMessage());
            bindingResult.rejectValue("email", "email.not.exist", e.getMessage());
            return FORGOT_PASSWORD;
        }
    }

    private void sendResetPasswordMail(HttpServletRequest request, UserDTO userDTO) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        userService.sendPasswordResetEmail(baseUrl, userDTO);
    }
}
