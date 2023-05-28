package com.sivalabs.techbuzz.users.web.controllers;

import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.users.domain.dtos.ResendVerificationRequest;
import com.sivalabs.techbuzz.users.domain.dtos.UserDTO;
import com.sivalabs.techbuzz.users.domain.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Optional;
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
class ResendVerificationController {

    private static final Logger logger = LoggerFactory.getLogger(ResendVerificationController.class);
    private static final String RESEND_VERIFICATION_EMAIL = "users/resendVerification";

    private final UserService userService;

    public ResendVerificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/resendVerification")
    public String resendVerificationForm(Model model) {
        model.addAttribute("resendEmail", new ResendVerificationRequest(""));
        return RESEND_VERIFICATION_EMAIL;
    }

    @PostMapping("/resendVerification")
    public String resendVerification(
            HttpServletRequest request,
            @Valid @ModelAttribute("resendEmail") ResendVerificationRequest resendVerificationRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return RESEND_VERIFICATION_EMAIL;
        }

        try {
            Optional<UserDTO> userDTO = userService.getUserDTO(resendVerificationRequest.email());
            if (userDTO.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Account not found with given email");
                return "redirect:/resendVerification";
            }
            var user = userDTO.get();
            if (user.verified()) {
                redirectAttributes.addFlashAttribute(
                        "errorMessage", "Account is already verified, please use forget password if needed");
            } else {
                this.sendVerificationEmail(request, user);
                logger.info("Sent email verification link to {}", resendVerificationRequest.email());
                redirectAttributes.addFlashAttribute("message", "Email verification link is sent to your email");
            }
        } catch (Exception e) {
            logger.error("Error during resending email verification request error: {}", e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Resending verification email failed, please try again");
        }
        return "redirect:/resendVerification";
    }

    private void sendVerificationEmail(HttpServletRequest request, UserDTO userDTO) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        userService.sendVerificationEmail(baseUrl, userDTO);
    }
}
