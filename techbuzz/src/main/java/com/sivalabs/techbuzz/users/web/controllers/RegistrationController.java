package com.sivalabs.techbuzz.users.web.controllers;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.sivalabs.techbuzz.common.exceptions.ResourceAlreadyExistsException;
import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.notifications.EmailService;
import com.sivalabs.techbuzz.users.domain.dtos.CreateUserRequest;
import com.sivalabs.techbuzz.users.domain.dtos.ResentVerificationRequest;
import com.sivalabs.techbuzz.users.domain.dtos.UserDTO;
import com.sivalabs.techbuzz.users.domain.models.User;
import com.sivalabs.techbuzz.users.domain.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
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
class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private static final String REGISTRATION_VIEW = "users/registration";

    private final UserService userService;
    private final EmailService emailService;

    public RegistrationController(final UserService userService, final EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
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
            return REGISTRATION_VIEW;
        }
        try {
            UserDTO userDTO = userService.createUser(createUserRequest);
            this.sendVerificationEmail(request, userDTO);
            redirectAttributes.addFlashAttribute("message", "Registration is successful");
            return "redirect:/registrationStatus";
        } catch (ResourceAlreadyExistsException e) {
            logger.error("Registration error: {}", e.getMessage());
            bindingResult.rejectValue("email", "email.exists", e.getMessage());
            return REGISTRATION_VIEW;
        }
    }

    @GetMapping("/registrationStatus")
    public String registrationStatus() {
        return "users/registrationStatus";
    }

    private void sendVerificationEmail(HttpServletRequest request, UserDTO userDTO) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        String params =
                "email=" + encode(userDTO.email(), UTF_8) + "&token=" + encode(userDTO.verificationToken(), UTF_8);
        String verificationUrl = baseUrl + "/verify-email?" + params;
        String to = userDTO.email();
        String subject = "TechBuzz - Email verification";
        Map<String, Object> paramsMap = Map.of("", userDTO.name(), "verificationUrl", verificationUrl);
        emailService.sendEmail("email/verify-email", paramsMap, to, subject);
    }

}
