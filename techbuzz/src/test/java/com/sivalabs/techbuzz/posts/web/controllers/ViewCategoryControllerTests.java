package com.sivalabs.techbuzz.posts.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ViewCategoryControllerTests extends AbstractIntegrationTest {

    @ParameterizedTest
    @CsvSource({"go", "java"})
    void shouldFetchPostsByCategory(String category) throws Exception {
        mockMvc.perform(get("/c/{category}", category))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/category"))
                .andExpect(model().attributeExists("paginationPrefix"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attributeExists("postsData"))
                .andExpect(model().attributeExists("categories"));
    }
}
