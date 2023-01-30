package com.sivalabs.techbuzz.posts.web.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

class AddVoteControllerTest extends AbstractIntegrationTest {
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
    void shouldUpVotePost() throws Exception {
        User user = securityService.loginUser();
        mockMvc.perform(
                        post("/api/votes")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                        {
                            "postId": "%d",
                            "userId": "%d",
                            "value": "1"
                        }
                        """
                                                .formatted(post.id(), user.getId())))
                .andExpect(status().isOk());
    }
}
