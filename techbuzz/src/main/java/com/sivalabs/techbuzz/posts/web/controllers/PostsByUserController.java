package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.common.model.PagedResult;
import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.posts.domain.dtos.PostViewDTO;
import com.sivalabs.techbuzz.posts.domain.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Loggable
public class PostsByUserController {
    private static final Logger log = LoggerFactory.getLogger(ViewCategoryController.class);

    private final PostService postService;

    public PostsByUserController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/userSpecific/posts/{userId}/{tab}")
    public String getUserSpecificPosts(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "tab") String tab,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            Model model) {
        log.info("Fetching created posts for user {} with page: {}", userId, page);
        PagedResult<PostViewDTO> data = "created".equals(tab)
                ? postService.getCreatedPostsByUser(userId, page)
                : postService.getVotedPostsByUser(userId, page);
        String commonUrl = "/userSpecific/posts/" + userId + "/";
        model.addAttribute("paginationPrefix", commonUrl + tab + "?");
        model.addAttribute("postsData", data);
        model.addAttribute("currentTab", tab);

        return "fragments/user-posts";
    }
}
