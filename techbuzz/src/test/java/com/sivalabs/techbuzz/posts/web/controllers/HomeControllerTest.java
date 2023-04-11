package com.sivalabs.techbuzz.posts.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;

import org.junit.jupiter.api.Test;

class HomeControllerTest extends AbstractIntegrationTest {
    @Test
    void shouldHomePageWithCategoriesList() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/home"))
                .andExpect(model().attributeExists("categories"));
    }
}
