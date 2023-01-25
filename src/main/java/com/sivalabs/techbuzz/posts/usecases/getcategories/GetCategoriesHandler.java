package com.sivalabs.techbuzz.posts.usecases.getcategories;

import com.sivalabs.techbuzz.posts.domain.models.CategoryDTO;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import com.sivalabs.techbuzz.posts.mappers.CategoryDTOMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GetCategoriesHandler {
    private final CategoryRepository categoryRepository;
    private final CategoryDTOMapper categoryDTOMapper;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll(Sort.by("displayOrder")).stream()
                .map(categoryDTOMapper::toDTO)
                .toList();
    }

    public CategoryDTO getCategory(String categorySlug) {
        return categoryDTOMapper.toDTO(categoryRepository.findBySlug(categorySlug).orElseThrow());
    }
}
