package com.sivalabs.techbuzz.posts.usecases.getcategories;

import com.sivalabs.techbuzz.posts.domain.models.CategoryDTO;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import com.sivalabs.techbuzz.posts.mappers.CategoryDTOMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetCategoriesHandler {
    private static final Logger log = LoggerFactory.getLogger(GetCategoriesHandler.class);

    private final CategoryRepository categoryRepository;
    private final CategoryDTOMapper categoryDTOMapper;

    public GetCategoriesHandler(
            final CategoryRepository categoryRepository, final CategoryDTOMapper categoryDTOMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryDTOMapper = categoryDTOMapper;
    }

    @Cacheable("categories")
    public List<CategoryDTO> getAllCategories() {
        log.debug("Fetching all categories");
        return categoryRepository.findAll(Sort.by("displayOrder")).stream()
                .map(categoryDTOMapper::toDTO)
                .toList();
    }

    @Cacheable("category")
    public CategoryDTO getCategory(String categorySlug) {
        log.debug("Fetching category by slug: {}", categorySlug);
        return categoryDTOMapper.toDTO(
                categoryRepository.findBySlug(categorySlug).orElseThrow());
    }
}
