package com.sivalabs.techbuzz.posts.usecases.getcategories;

import com.sivalabs.techbuzz.posts.domain.models.CategoryDTO;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import com.sivalabs.techbuzz.posts.mappers.CategoryDTOMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GetCategoriesHandler {
    private final CategoryRepository categoryRepository;
    private final CategoryDTOMapper categoryDTOMapper;

    @Cacheable(value = "categories")
    public List<CategoryDTO> getAllCategories() {
        log.debug("Fetching all categories");
        return categoryRepository.findAll(Sort.by("displayOrder")).stream()
                .map(categoryDTOMapper::toDTO)
                .toList();
    }

    @Cacheable(value = "category")
    public CategoryDTO getCategory(String categorySlug) {
        log.debug("Fetching category by slug: {}", categorySlug);
        return categoryDTOMapper.toDTO(categoryRepository.findBySlug(categorySlug).orElseThrow());
    }
}
