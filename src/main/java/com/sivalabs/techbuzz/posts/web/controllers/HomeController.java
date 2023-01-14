package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final CategoryRepository categoryRepository;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("categories", categoryRepository.findAll());
		return "index";
	}
}
