package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.common.model.PagedResult;
import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.posts.domain.dtos.PostViewDTO;
import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.services.CategoryService;
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
public class ViewCategoryController {
    private static final Logger log = LoggerFactory.getLogger(ViewCategoryController.class);

    private final PostService postService;
    private final CategoryService categoryService;

    public ViewCategoryController(PostService postService, CategoryService categoryService) {
        this.postService = postService;
        this.categoryService = categoryService;
    }

    @GetMapping("/c/{categorySlug}")
    public String viewCategory(
            @PathVariable(name = "categorySlug") String categorySlug,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            Model model) {
        log.info("Fetching posts for category {} with page: {}", categorySlug, page);
        PagedResult<PostViewDTO> data = postService.getPostsByCategorySlug(categorySlug, page);
        if (data.data().isEmpty() && (page > 1 && page > data.totalPages())) {
            return "redirect:/c/" + categorySlug + "?page=" + data.totalPages();
        }
        Category category = categoryService.getCategory(categorySlug);
        model.addAttribute("category", category);
        model.addAttribute("paginationPrefix", "/c/" + categorySlug + "?");
        model.addAttribute("postsData", data);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "posts/category";
    }
}
