package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.posts.usecases.getcategories.GetCategoriesHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class HomeController {
    private final GetCategoriesHandler getCategoriesHandler;

    public HomeController(GetCategoriesHandler getCategoriesHandler) {
        this.getCategoriesHandler = getCategoriesHandler;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("categories", getCategoriesHandler.getAllCategories());
        return "posts/home";
    }
}
