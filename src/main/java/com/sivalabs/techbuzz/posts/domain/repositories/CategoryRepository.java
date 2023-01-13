package com.sivalabs.techbuzz.posts.domain.repositories;

import com.sivalabs.techbuzz.posts.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findBySlug(String slug);

}
