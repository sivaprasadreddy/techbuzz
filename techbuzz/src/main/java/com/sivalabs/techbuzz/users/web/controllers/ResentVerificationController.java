package com.sivalabs.techbuzz.users.web.controllers;

import com.sivalabs.techbuzz.users.domain.dtos.ResentVerificationRequest;
import com.sivalabs.techbuzz.users.domain.dtos.UserDTO;
import com.sivalabs.techbuzz.users.domain.models.User;
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

@Controller
public class ResentVerificationController {

    private static final Logger logger = LoggerFactory.getLogger(ResentVerificationController.class);
    private static final String RESENT_VERIFICATION_EMAIL = "users/resentVerification";

    private final UserService userService;

    public ResentVerificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/resentVerification")
    public String resentVerificationForm(Model model) {
        model.addAttribute("resentEmail", new ResentVerificationRequest(""));
        return RESENT_VERIFICATION_EMAIL;
    }

    @PostMapping("/resentVerification")
    public String resentVerification(
            HttpServletRequest request,
            @Valid @ModelAttribute("resentEmail") ResentVerificationRequest resentVerificationRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return RESENT_VERIFICATION_EMAIL;
        }

        try {
            Optional<User> user = userService.getUserByEmail(resentVerificationRequest.email());
            if (user.isPresent()) {
                // if account is verified then redirect to login page with account exist message
                if (user.get().isVerified()) {
                    redirectAttributes.addFlashAttribute(
                            "errorMessage", "account is already verified, please use forget password if needed");
                    return "redirect:/login";
                }
                // if account is not-verified then resent email and redirect to registrationStatus page
                if (!user.get().isVerified()) {
                    Optional<UserDTO> existingUserDTO =
                            userService.getUserDTO(user.get().getEmail());
                    userService.sendVerificationEmail(request, existingUserDTO.get());
                    redirectAttributes.addFlashAttribute(
                            "message", "reset verification link is successful please check your email");
                    return "redirect:/registrationStatus";
                }
            }
        } catch (Exception e) {
            logger.error("error during resending email verification request error: {}", e.getMessage());
            return RESENT_VERIFICATION_EMAIL;
        }

        redirectAttributes.addFlashAttribute("errorMessage", "reset verification failed, please try re-register");
        return "redirect:/registration";
    }
}
