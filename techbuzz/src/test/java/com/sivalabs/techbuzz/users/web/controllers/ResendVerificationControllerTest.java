package com.sivalabs.techbuzz.users.web.controllers;

import static org.instancio.Select.field;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import com.sivalabs.techbuzz.users.domain.dtos.CreateUserRequest;
import com.sivalabs.techbuzz.users.domain.dtos.UserDTO;
import com.sivalabs.techbuzz.users.domain.services.UserService;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ResendVerificationControllerTest extends AbstractIntegrationTest {
    @Autowired
    UserService userService;

    @Test
    void shouldShowResendVerificationFormPage() throws Exception {
        mockMvc.perform(get("/resendVerification"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/resendVerification"))
                .andExpect(model().attributeExists("resendEmail"));
    }

    @Test
    void shouldResendVerificationFormPageWhenSubmittedInvalidData() throws Exception {
        mockMvc.perform(post("/resendVerification").with(csrf()).param("email", ""))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("resendEmail", "email", "NotBlank"))
                .andExpect(view().name("users/resendVerification"));
    }

    @Test
    void shouldResendVerificationEmailSuccessfully() throws Exception {
        CreateUserRequest request = Instancio.of(CreateUserRequest.class)
                .set(field("email"), UUID.randomUUID() + "@gmail.com")
                .create();

        UserDTO user = userService.createUser(request);
        mockMvc.perform(post("/resendVerification").with(csrf()).param("email", user.email()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "Email verification link is sent to your email"))
                .andExpect(view().name("redirect:/resendVerification"));
    }

    @Test
    void shouldDisplayErrorIfEmailDoesNotExist() throws Exception {
        String email = UUID.randomUUID() + "@gmail.com";

        mockMvc.perform(post("/resendVerification").with(csrf()).param("email", email))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("errorMessage", "Account not found with given email"))
                .andExpect(view().name("redirect:/resendVerification"));
    }

    @Test
    void shouldDisplayErrorIfEmailAlreadyVerified() throws Exception {
        String email = UUID.randomUUID() + "@gmail.com";
        CreateUserRequest request =
                Instancio.of(CreateUserRequest.class).set(field("email"), email).create();

        UserDTO user = userService.createUser(request);
        userService.verifyEmail(user.email(), user.verificationToken());

        mockMvc.perform(post("/resendVerification").with(csrf()).param("email", email))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute(
                                "errorMessage", "Account is already verified, please use forget password if needed"))
                .andExpect(view().name("redirect:/resendVerification"));
    }
}
