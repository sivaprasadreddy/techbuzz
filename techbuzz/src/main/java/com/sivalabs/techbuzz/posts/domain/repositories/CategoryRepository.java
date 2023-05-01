package com.sivalabs.techbuzz.posts.domain.repositories;

import com.sivalabs.techbuzz.posts.domain.entities.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findBySlug(String slug);

    Category getReferenceById(Long aLong);

    List<Category> findAll();
}
