package com.sivalabs.techbuzz.posts.adapter.repositories;

import static com.sivalabs.techbuzz.jooq.tables.Categories.CATEGORIES;

import com.sivalabs.techbuzz.jooq.tables.records.CategoriesRecord;
import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
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
        return this.dsl
                .selectFrom(CATEGORIES)
                .where(CATEGORIES.SLUG.eq(slug))
                .fetchOptional(CategoryRecordMapper.INSTANCE);
    }

    @Override
    public Category findById(Long id) {
        return this.dsl.selectFrom(CATEGORIES).where(CATEGORIES.ID.eq(id)).fetchSingle(CategoryRecordMapper.INSTANCE);
    }

    @Override
    public List<Category> findAll() {
        return this.dsl.selectFrom(CATEGORIES).orderBy(CATEGORIES.DISPLAY_ORDER).fetch(CategoryRecordMapper.INSTANCE);
    }

    static class CategoryRecordMapper implements RecordMapper<CategoriesRecord, Category> {
        static final CategoryRecordMapper INSTANCE = new CategoryRecordMapper();

        private CategoryRecordMapper() {}

        @Override
        public Category map(CategoriesRecord r) {
            return new Category(
                    r.getId(),
                    r.getName(),
                    r.getSlug(),
                    r.getDescription(),
                    r.getImage(),
                    r.getDisplayOrder(),
                    r.getCreatedAt(),
                    r.getUpdatedAt());
        }
    }
}
