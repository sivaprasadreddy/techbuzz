package com.sivalabs.techbuzz.posts.web.controllers;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;

class CreatePostControllerTest extends AbstractIntegrationTest {

    @Test
    @WithUserDetails(value = "sivaprasadreddy.k@gmail.com")
    void shouldShowCreatePostFormPage() throws Exception {
        mockMvc.perform(get("/posts/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-post"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    @WithUserDetails(value = "sivaprasadreddy.k@gmail.com")
    void shouldCreatePostSuccessfully() throws Exception {
        mockMvc.perform(
                        post("/posts")
                                .with(csrf())
                                .param("url", "https://sivalabs.in")
                                .param("title", "SivaLabs")
                                .param("content", "demo content")
                                .param("categoryId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "Post saved successfully"))
                .andExpect(view().name(matchesPattern("redirect:/posts/.*/edit")));
        ;
    }

    @Test
    @WithUserDetails(value = "sivaprasadreddy.k@gmail.com")
    void shouldFailToCreatePostIfDataIsInvalid() throws Exception {
        mockMvc.perform(
                        post("/posts")
                                .with(csrf())
                                .param("url", "")
                                .param("title", "")
                                .param("content", "")
                                .param("categoryId", ""))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("post", "title", "NotEmpty"))
                .andExpect(model().attributeHasFieldErrorCode("post", "content", "NotEmpty"))
                .andExpect(model().attributeHasFieldErrorCode("post", "categoryId", "NotNull"))
                .andExpect(view().name("add-post"));
    }
}
