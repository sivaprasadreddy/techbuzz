package com.sivalabs.techbuzz.users.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.ResourceAlreadyExistsException;
import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.users.domain.dtos.CreateUserRequest;
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
class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private static final String REGISTRATION_VIEW = "users/registration";

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        logger.info("Received request for registration form");
        model.addAttribute("user", new CreateUserRequest("", "", ""));
        return REGISTRATION_VIEW;
    }

    @PostMapping("/registration")
    public String registerUser(
            HttpServletRequest request,
            @Valid @ModelAttribute("user") CreateUserRequest createUserRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.warn("Registration form has validation errors");
            return REGISTRATION_VIEW;
        }
        try {
            logger.info("POST /registration - Registering user");
            UserDTO userDTO = userService.createUser(createUserRequest);
            this.sendVerificationEmail(request, userDTO);
            redirectAttributes.addFlashAttribute("message", "Registration is successful");
            logger.info("User successfully registered: {}", userDTO.email());
            return "redirect:/registrationStatus";
        } catch (ResourceAlreadyExistsException e) {
            logger.error("Registration error: {}", e.getMessage());
            bindingResult.rejectValue("email", "email.exists", e.getMessage());
            return REGISTRATION_VIEW;
        }
    }

    @GetMapping("/registrationStatus")
    public String registrationStatus() {
        logger.info("GET /registrationStatus");
        return "users/registrationStatus";
    }

    private void sendVerificationEmail(HttpServletRequest request, UserDTO userDTO) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        userService.sendVerificationEmail(baseUrl, userDTO);
    }
}
