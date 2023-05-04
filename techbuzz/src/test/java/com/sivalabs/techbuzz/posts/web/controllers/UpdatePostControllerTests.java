package com.sivalabs.techbuzz.posts.web.controllers;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import com.sivalabs.techbuzz.posts.domain.dtos.CreatePostRequest;
import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.services.CategoryService;
import com.sivalabs.techbuzz.posts.domain.services.PostService;
import com.sivalabs.techbuzz.security.SecurityService;
import com.sivalabs.techbuzz.users.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

class UpdatePostControllerTests extends AbstractIntegrationTest {
    @Autowired
    CategoryService categoryService;

    @Autowired
    PostService postService;

    @Autowired
    SecurityService securityService;

    Post post = null;

    @BeforeEach
    void setUp() {
        Category category = categoryService.getCategory("java");
        User user = securityService.loginUser();
        CreatePostRequest request =
                new CreatePostRequest("title", "https://sivalabs.in", "test content", category.getId(), user.getId());
        post = postService.createPost(request);
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void shouldShowUpdatePostFormPage() throws Exception {
        mockMvc.perform(get("/posts/{id}/edit", post.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/edit-post"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void shouldUpdatePostSuccessfully() throws Exception {
        mockMvc.perform(put("/posts/{id}", post.getId())
                        .with(csrf())
                        .param("url", "https://sivalabs.in")
                        .param("title", "SivaLabs")
                        .param("content", "demo content")
                        .param("categoryId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "Post updated successfully"))
                .andExpect(view().name(matchesPattern("redirect:/posts/.*/edit")));
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void shouldFailToUpdatePostIfDataIsInvalid() throws Exception {
        mockMvc.perform(put("/posts/{id}", post.getId())
                        .with(csrf())
                        .param("url", "")
                        .param("title", "")
                        .param("content", "")
                        .param("categoryId", ""))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("post", "title", "NotEmpty"))
                .andExpect(model().attributeHasFieldErrorCode("post", "content", "NotEmpty"))
                .andExpect(model().attributeHasFieldErrorCode("post", "categoryId", "NotNull"))
                .andExpect(view().name("posts/edit-post"));
    }
}
