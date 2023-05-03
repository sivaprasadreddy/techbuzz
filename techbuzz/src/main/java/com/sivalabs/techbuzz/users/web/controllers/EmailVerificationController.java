package com.sivalabs.techbuzz.users.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.TechBuzzException;
import com.sivalabs.techbuzz.users.usecases.verifyemail.EmailVerificationHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class EmailVerificationController {
    private final EmailVerificationHandler emailVerificationHandler;

    public EmailVerificationController(final EmailVerificationHandler emailVerificationHandler) {
        this.emailVerificationHandler = emailVerificationHandler;
    }

    @GetMapping("/verify-email")
    public String verifyEmail(Model model, @RequestParam("email") String email, @RequestParam("token") String token) {
        try {
            emailVerificationHandler.verify(email, token);
            model.addAttribute("success", true);
        } catch (TechBuzzException e) {
            model.addAttribute("success", false);
        }
        return "users/emailVerification";
    }
}
