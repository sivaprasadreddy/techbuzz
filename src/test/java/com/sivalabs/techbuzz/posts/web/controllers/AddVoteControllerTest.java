package com.sivalabs.techbuzz.posts.web.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import com.sivalabs.techbuzz.posts.domain.entities.Category;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostHandler;
import com.sivalabs.techbuzz.posts.usecases.createpost.CreatePostRequest;
import com.sivalabs.techbuzz.posts.usecases.getposts.GetPostsHandler;
import com.sivalabs.techbuzz.security.SecurityService;
import com.sivalabs.techbuzz.users.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

class AddVoteControllerTest extends AbstractIntegrationTest {
    @Autowired CreatePostHandler createPostHandler;
    @Autowired GetPostsHandler getPostsHandler;
    @Autowired SecurityService securityService;

    Post post = null;

    @BeforeEach
    void setUp() {
        Category category = getPostsHandler.getCategory("java");
        User user = securityService.loginUser();
        CreatePostRequest request =
                new CreatePostRequest(
                        "title",
                        "https://sivalabs.in",
                        "test content",
                        category.getId(),
                        user.getId());
        post = createPostHandler.createPost(request);
    }

    @Test
    @WithUserDetails(value = "sivaprasadreddy.k@gmail.com")
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
                                                .formatted(post.getId(), user.getId())))
                .andExpect(status().isOk());
    }
}
