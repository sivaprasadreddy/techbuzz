package com.sivalabs.techbuzz.posts.domain.repositories;

import com.sivalabs.techbuzz.posts.domain.models.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findBySlug(String slug);

    Category findById(Long id);

    List<Category> findAll();
}
