package com.sivalabs.techbuzz.posts.web.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import com.sivalabs.techbuzz.posts.domain.models.CategoryDTO;
import com.sivalabs.techbuzz.posts.domain.models.PostDTO;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostHandler;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostRequest;
import com.sivalabs.techbuzz.posts.usecases.getcategories.GetCategoriesHandler;
import com.sivalabs.techbuzz.security.SecurityService;
import com.sivalabs.techbuzz.users.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

class DeletePostControllerTests extends AbstractIntegrationTest {
    @Autowired CreatePostHandler createPostHandler;
    @Autowired GetCategoriesHandler getCategoriesHandler;
    @Autowired SecurityService securityService;

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void shouldDeletePost() throws Exception {
        CategoryDTO category = getCategoriesHandler.getCategory("java");
        User user = securityService.loginUser();
        CreatePostRequest request =
                new CreatePostRequest(
                        "title",
                        "https://sivalabs.in",
                        "test content",
                        category.id(),
                        user.getId());
        PostDTO post = createPostHandler.createPost(request);
        mockMvc.perform(delete("/posts/{id}", post.id()).with(csrf())).andExpect(status().isOk());
    }
}
