package com.sivalabs.techbuzz.posts.domain.repositories;

import com.sivalabs.techbuzz.posts.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Modifying
    @Query(value = "insert into categories(name) values(:#{#c.name}) ON CONFLICT DO NOTHING", nativeQuery = true)
    void upsert(@Param("c") Category category);

    default Category getOrCreateCategory(String categoryName) {
        Optional<Category> optionalCategory = this.findByName(categoryName);
        if(optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        this.upsert(new Category(null, categoryName));
        return this.findByName(categoryName).orElseThrow();
    }
}
