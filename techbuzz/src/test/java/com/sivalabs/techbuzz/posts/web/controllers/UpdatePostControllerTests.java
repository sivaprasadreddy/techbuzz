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
import com.sivalabs.techbuzz.posts.domain.models.CategoryDTO;
import com.sivalabs.techbuzz.posts.domain.models.PostDTO;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostHandler;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostRequest;
import com.sivalabs.techbuzz.posts.usecases.getcategories.GetCategoriesHandler;
import com.sivalabs.techbuzz.security.SecurityService;
import com.sivalabs.techbuzz.users.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

class UpdatePostControllerTests extends AbstractIntegrationTest {
    @Autowired CreatePostHandler createPostHandler;
    @Autowired GetCategoriesHandler getCategoriesHandler;
    @Autowired SecurityService securityService;

    PostDTO post = null;

    @BeforeEach
    void setUp() {
        CategoryDTO category = getCategoriesHandler.getCategory("java");
        User user = securityService.loginUser();
        CreatePostRequest request =
                new CreatePostRequest(
                        "title",
                        "https://sivalabs.in",
                        "test content",
                        category.id(),
                        user.getId());
        post = createPostHandler.createPost(request);
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void shouldShowUpdatePostFormPage() throws Exception {
        mockMvc.perform(get("/posts/{id}/edit", post.id()))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/edit-post"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void shouldUpdatePostSuccessfully() throws Exception {
        mockMvc.perform(
                        put("/posts/{id}", post.id())
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
        mockMvc.perform(
                        put("/posts/{id}", post.id())
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
