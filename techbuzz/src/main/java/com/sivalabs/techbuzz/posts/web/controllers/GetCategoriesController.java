package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@Loggable
public class GetCategoriesController {
    private static final Logger log = LoggerFactory.getLogger(GetCategoriesController.class);
    private final CategoryRepository categoryRepository;

    public GetCategoriesController(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> allCategories() {
        log.info("GET /api/categories - Fetching all categories");
        return categoryRepository.findAll();
    }
}
