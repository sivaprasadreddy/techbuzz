package com.sivalabs.techbuzz.posts.mappers;

import com.sivalabs.techbuzz.posts.domain.entities.Category;
import com.sivalabs.techbuzz.posts.domain.models.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryDTOMapper {

    public CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                category.getImage(),
                category.getDisplayOrder(),
                category.getCreatedAt(),
                category.getUpdatedAt());
    }
}
