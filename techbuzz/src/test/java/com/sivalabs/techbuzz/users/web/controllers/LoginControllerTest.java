package com.sivalabs.techbuzz.users.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;

import org.junit.jupiter.api.Test;

class LoginControllerTest extends AbstractIntegrationTest {

    @Test
    void shouldShowLoginFormPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"));
    }
}
