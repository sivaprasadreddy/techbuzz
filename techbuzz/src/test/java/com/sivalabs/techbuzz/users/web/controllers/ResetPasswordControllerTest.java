package com.sivalabs.techbuzz.users.web.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

public class ResetPasswordControllerTest extends AbstractIntegrationTest {

    @Test
    @Sql("/users_with_reset_password_token.sql")
    void shouldShowResetPasswordPage() throws Exception {

        mockMvc.perform(get("/reset-password")
                        .with(csrf())
                        .param("email", ADMIN_EMAIL)
                        .param("token", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/resetPassword"))
                .andExpect(model().attributeExists("resetPassword"));
    }

    @Test
    void shouldRedisplayForgotPasswordPageWithErrorWhenSubmittedInvalidEmail() throws Exception {
        mockMvc.perform(get("/reset-password")
                        .with(csrf())
                        .param("email", "test@test.com")
                        .param("token", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("errorMessage", "Password reset  failed, please try again"))
                .andExpect(header().string("Location", "/reset-password"));
    }

    @Test
    @Sql("/users_with_reset_password_token.sql")
    void shouldRedisplayForgotPasswordPageWithErrorWhenSubmittedInvalidToken() throws Exception {
        mockMvc.perform(get("/reset-password")
                        .with(csrf())
                        .param("email", ADMIN_EMAIL)
                        .param("token", "test123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("errorMessage", "Password reset  failed, please try again"))
                .andExpect(header().string("Location", "/reset-password"));
    }

    @Test
    @Sql("/users_with_reset_password_token.sql")
    void shouldUpdatePassword() throws Exception {
        mockMvc.perform(post("/reset-password")
                        .with(csrf())
                        .param("email", ADMIN_EMAIL)
                        .param("token", "test")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "Password reset is successful"))
                .andExpect(header().string("Location", "/login"));
    }

    @Test
    void shouldRedisplayResetPasswordPageWhenSubmittedInvalidData() throws Exception {
        mockMvc.perform(post("/reset-password").with(csrf()).param("email", "").param("token", "test"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("resetPassword", "email", "NotBlank"))
                .andExpect(view().name("users/resetPassword"));
    }
}
