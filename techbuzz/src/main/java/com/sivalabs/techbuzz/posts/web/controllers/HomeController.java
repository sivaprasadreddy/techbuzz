package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.config.logging.Loggable;
import com.sivalabs.techbuzz.posts.domain.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Loggable
class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private final CategoryService categoryService;

    HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        log.info("Handle home request");
        model.addAttribute("categories", categoryService.getAllCategories());
        return "posts/home";
    }
}
