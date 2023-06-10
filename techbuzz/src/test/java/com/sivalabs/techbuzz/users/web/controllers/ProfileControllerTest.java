package com.sivalabs.techbuzz.users.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

public class ProfileControllerTest extends AbstractIntegrationTest {
    @Test
    void shouldFetchUserProfileDetails() throws Exception {
        mockMvc.perform(get("/users/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("users/profile"))
                .andExpect(model().attributeExists("userProfile"))
                .andExpect(model().attributeExists("userSpecificPostsUrl"));
    }

    @Test
    void shouldReturn404PageForInvalidUser() throws Exception {
        mockMvc.perform(get("/users/{userId}", 10L)).andExpect(status().isNotFound());
    }
}
