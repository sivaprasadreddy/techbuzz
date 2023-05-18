package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@Loggable
public class GetCategoriesController {
    private final CategoryRepository categoryRepository;

    public GetCategoriesController(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }
}
