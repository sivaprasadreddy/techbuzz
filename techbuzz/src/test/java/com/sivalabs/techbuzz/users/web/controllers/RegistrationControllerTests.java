package com.sivalabs.techbuzz.users.web.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

class RegistrationControllerTests extends AbstractIntegrationTest {

    // @MockBean
    // EmailService emailService;

    @Test
    void shouldShowRegistrationFormPage() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/registration"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void shouldRegisterSuccessfully() throws Exception {
        mockMvc.perform(post("/registration")
                        .with(csrf())
                        .param("name", "dummy")
                        .param("email", "dummy@mail.com")
                        .param("password", "admin1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "Registration is successful"))
                .andExpect(header().string("Location", "/registrationStatus"));
    }

    @Test
    void shouldRedisplayRegistrationFormPageWhenSubmittedInvalidData() throws Exception {
        mockMvc.perform(post("/registration")
                        .with(csrf())
                        .param("name", "")
                        .param("email", "")
                        .param("password", ""))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "name", "email", "password"))
                .andExpect(model().attributeHasFieldErrorCode("user", "name", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("user", "email", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "NotBlank"))
                .andExpect(view().name("users/registration"));
    }

    @Test
    void shouldRedisplayRegistrationFormPageWhenEmailAlreadyExists() throws Exception {
        mockMvc.perform(post("/registration")
                        .with(csrf())
                        .param("name", "Siva")
                        .param("email", ADMIN_EMAIL)
                        .param("password", "siva"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(model().attributeHasFieldErrorCode("user", "email", "email.exists"))
                .andExpect(view().name("users/registration"));
    }
}
