package com.sivalabs.techbuzz.users.web.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

public class ForgotPasswordControllerTest extends AbstractIntegrationTest {
    @Test
    void shouldShowForgotPasswordPage() throws Exception {
        mockMvc.perform(get("/forgot-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/forgotPassword"))
                .andExpect(model().attributeExists("forgotPassword"));
    }

    @Test
    void shouldSendPasswordResetMail() throws Exception {
        mockMvc.perform(post("/forgot-password").with(csrf()).param("email", ADMIN_EMAIL))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "Password reset link is sent to your email"))
                .andExpect(header().string("Location", "/forgot-password"));
    }

    @Test
    void shouldRedisplayForgotPasswordPageWhenSubmittedInvalidData() throws Exception {
        mockMvc.perform(post("/forgot-password").with(csrf()).param("email", ""))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("forgotPassword", "email", "NotBlank"))
                .andExpect(view().name("users/forgotPassword"));
    }

    @Test
    void shouldRedisplayPasswordPageWhenEmailNotExist() throws Exception {
        mockMvc.perform(post("/forgot-password").with(csrf()).param("email", "test@test.com"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("forgotPassword", "email"))
                .andExpect(model().attributeHasFieldErrorCode("forgotPassword", "email", "email.not.exist"))
                .andExpect(view().name("users/forgotPassword"));
    }
}
