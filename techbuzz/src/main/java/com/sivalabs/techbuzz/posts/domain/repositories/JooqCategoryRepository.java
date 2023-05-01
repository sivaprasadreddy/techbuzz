package com.sivalabs.techbuzz.posts.domain.repositories;

import static com.sivalabs.techbuzz.jooq.tables.Categories.CATEGORIES;

import com.sivalabs.techbuzz.jooq.tables.records.CategoriesRecord;
import com.sivalabs.techbuzz.posts.domain.entities.Category;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

@Repository
class JooqCategoryRepository implements CategoryRepository {
    private final DSLContext dsl;

    JooqCategoryRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        return this.dsl.selectFrom(CATEGORIES).where(CATEGORIES.SLUG.eq(slug)).fetchOptional(mapToCategory());
    }

    @Override
    public Category getReferenceById(Long id) {
        return this.dsl.selectFrom(CATEGORIES).where(CATEGORIES.ID.eq(id)).fetchSingle(mapToCategory());
    }

    @Override
    public List<Category> findAll() {
        return this.dsl.selectFrom(CATEGORIES).orderBy(CATEGORIES.DISPLAY_ORDER).fetch(mapToCategory());
    }

    private static RecordMapper<CategoriesRecord, Category> mapToCategory() {
        return r -> new Category(
                r.getId(),
                r.getName(),
                r.getSlug(),
                r.getDescription(),
                r.getImage(),
                r.getDisplayOrder().intValue(),
                r.getCreatedAt(),
                r.getUpdatedAt());
    }
}
