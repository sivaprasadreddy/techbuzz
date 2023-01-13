package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.posts.domain.entities.Category;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class GetCategoriesController {

	private final CategoryRepository categoryRepository;

	@GetMapping
	public List<Category> allCategories() {
		return categoryRepository.findAll();
	}

}
