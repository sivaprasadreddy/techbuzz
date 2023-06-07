package com.sivalabs.techbuzz.posts.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import com.sivalabs.techbuzz.posts.domain.dtos.CreatePostRequest;
import com.sivalabs.techbuzz.posts.domain.dtos.CreateVoteRequest;
import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.services.CategoryService;
import com.sivalabs.techbuzz.posts.domain.services.PostService;
import com.sivalabs.techbuzz.security.SecurityService;
import com.sivalabs.techbuzz.users.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

public class PostsByUserControllerTest extends AbstractIntegrationTest {

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
        CreateVoteRequest voteRequest = new CreateVoteRequest(post.getId(), user.getId(), 1);
        postService.addVote(voteRequest);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
    1,created
    1,voted
    """)
    @WithUserDetails(value = ADMIN_EMAIL)
    void shouldFetchPostsByUser(Long userId, String tab) throws Exception {
        mockMvc.perform(get("/userSpecific/posts/{userId}/{tab}", userId, tab))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments/user-posts"))
                .andExpect(model().attributeExists("paginationPrefix"))
                .andExpect(model().attributeExists("currentTab"))
                .andExpect(model().attributeExists("postsData"));
    }
}
