package com.sivalabs.techbuzz.users.web.controllers;

import com.sivalabs.techbuzz.config.logging.Loggable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Loggable
class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        if (request.getParameterMap().containsKey("error")) {
            HttpSession session = request.getSession(false);
            String errorMessage = "Failed to login, Try again";
            if (session != null) {
                AuthenticationException ex =
                        (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                if (ex != null) {
                    errorMessage = ex.getMessage();
                }
            }
            model.addAttribute("errorMessage", errorMessage);
            log.error(errorMessage);
        }
        return "users/login";
    }
}
