package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.common.model.PagedResult;
import com.sivalabs.techbuzz.posts.domain.models.CategoryDTO;
import com.sivalabs.techbuzz.posts.domain.models.PostUserViewDTO;
import com.sivalabs.techbuzz.posts.usecases.getcategories.GetCategoriesHandler;
import com.sivalabs.techbuzz.posts.usecases.getposts.GetPostsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewCategoryController {
    private static final Logger log = LoggerFactory.getLogger(ViewCategoryController.class);

    private final GetPostsHandler getPostsHandler;
    private final GetCategoriesHandler getCategoriesHandler;

    public ViewCategoryController(
            final GetPostsHandler getPostsHandler, final GetCategoriesHandler getCategoriesHandler) {
        this.getPostsHandler = getPostsHandler;
        this.getCategoriesHandler = getCategoriesHandler;
    }

    @GetMapping("/c/{categorySlug}")
    public String viewCategory(
            @PathVariable(name = "categorySlug") String categorySlug,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            Model model) {
        log.info("Fetching posts for category {} with page: {}", categorySlug, page);
        PagedResult<PostUserViewDTO> data = getPostsHandler.getPostsByCategorySlug(categorySlug, page);
        if (data.getData().isEmpty() && (page > 1 && page > data.getTotalPages())) {
            return "redirect:/c/" + categorySlug + "?page=" + data.getTotalPages();
        }
        CategoryDTO category = getCategoriesHandler.getCategory(categorySlug);
        model.addAttribute("category", category);
        model.addAttribute("paginationPrefix", "/c/" + categorySlug + "?");
        model.addAttribute("postsData", data);
        model.addAttribute("categories", getCategoriesHandler.getAllCategories());
        return "posts/category";
    }
}
