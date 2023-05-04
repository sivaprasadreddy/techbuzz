package com.sivalabs.techbuzz.posts.adapter.repositories;

import static com.sivalabs.techbuzz.jooq.Tables.CATEGORIES;
import static com.sivalabs.techbuzz.jooq.Tables.VOTES;
import static com.sivalabs.techbuzz.jooq.tables.Posts.POSTS;
import static org.jooq.Records.mapping;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.row;
import static org.jooq.impl.DSL.select;

import com.sivalabs.techbuzz.ApplicationProperties;
import com.sivalabs.techbuzz.common.model.PagedResult;
import com.sivalabs.techbuzz.jooq.tables.records.PostsRecord;
import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.models.Vote;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.users.domain.models.User;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Function2;
import org.jooq.Function3;
import org.jooq.Function4;
import org.jooq.Record9;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

@Repository
class JooqPostRepository implements PostRepository {
    private final DSLContext dsl;
    private final ApplicationProperties properties;

    JooqPostRepository(DSLContext dsl, ApplicationProperties properties) {
        this.dsl = dsl;
        this.properties = properties;
    }

    @Override
    public PagedResult<Post> findByCategorySlug(String categorySlug, Integer page) {
        int totalElements = this.dsl.fetchCount(
                POSTS.join(CATEGORIES).on(POSTS.CAT_ID.eq(CATEGORIES.ID)).where(CATEGORIES.SLUG.eq(categorySlug)));

        List<Long> postIds = this.dsl
                .selectDistinct(POSTS.ID, POSTS.CREATED_AT)
                .from(POSTS)
                .join(CATEGORIES)
                .on(POSTS.CAT_ID.eq(CATEGORIES.ID))
                .where(CATEGORIES.SLUG.eq(categorySlug))
                .orderBy(POSTS.CREATED_AT.desc())
                .limit(properties.postsPerPage())
                .offset((page - 1) * properties.postsPerPage())
                .fetch(POSTS.ID);

        List<Post> posts = findPosts(postIds);
        int totalPages = (int) Math.ceil((double) totalElements / (double) properties.postsPerPage());
        return new PagedResult<>(
                posts, totalElements, page, totalPages, page == 1, totalPages == page, totalPages > page, page > 1);
    }

    @Override
    public List<Post> findPosts(List<Long> postIds) {
        return selectPostSpec()
                .where(POSTS.ID.in(postIds))
                .fetch(r -> new Post(
                        r.value1(),
                        r.value2(),
                        r.value3(),
                        r.value4(),
                        r.value7(),
                        r.value8(),
                        new HashSet<>(r.value9()),
                        r.value5(),
                        r.value6()));
    }

    @Override
    public Optional<Post> findById(Long postId) {

        return selectPostSpec()
                .where(POSTS.ID.eq(postId))
                .fetchOptional(r -> new Post(
                        r.value1(),
                        r.value2(),
                        r.value3(),
                        r.value4(),
                        r.value7(),
                        r.value8(),
                        new HashSet<>(r.value9()),
                        r.value5(),
                        r.value6()));
    }

    private SelectJoinStep<
                    Record9<Long, String, String, String, LocalDateTime, LocalDateTime, Category, User, List<Vote>>>
            selectPostSpec() {
        return this.dsl
                .select(
                        POSTS.ID,
                        POSTS.TITLE,
                        POSTS.URL,
                        POSTS.CONTENT,
                        POSTS.CREATED_AT,
                        POSTS.UPDATED_AT,
                        row(POSTS.categories().ID, POSTS.categories().NAME, POSTS.categories().SLUG)
                                .mapping(mapToCategory())
                                .as("category"),
                        row(POSTS.users().ID, POSTS.users().NAME)
                                .mapping(mapToUser())
                                .as("user"),
                        multiset(select(VOTES.ID, VOTES.USER_ID, VOTES.POST_ID, VOTES.VAL)
                                        .from(VOTES)
                                        .where(POSTS.ID.eq(VOTES.POST_ID)))
                                .as("votes")
                                .convertFrom(r -> r.map(mapping(mapToVote()))))
                .from(POSTS);
    }

    private static Function4<Long, Long, Long, Integer, Vote> mapToVote() {
        return (id, userId, postId, val) -> new Vote(id, userId, postId, val, null, null);
    }

    private static Function3<Long, String, String, Category> mapToCategory() {
        return (id, name, slug) -> new Category(id, name, slug, null, null, null, null, null);
    }

    private static Function2<Long, String, User> mapToUser() {
        return (id, name) -> new User(id, name, null, null, null, true, null);
    }

    @Override
    public Post save(Post post) {
        PostsRecord postsRecord = this.dsl
                .insertInto(POSTS)
                .set(POSTS.TITLE, post.getTitle())
                .set(POSTS.URL, post.getUrl())
                .set(POSTS.CONTENT, post.getContent())
                .set(POSTS.CAT_ID, post.getCategory().getId())
                .set(POSTS.CREATED_BY, post.getCreatedBy().getId())
                .set(POSTS.CREATED_AT, post.getCreatedAt())
                .returning(POSTS.ID)
                .fetchSingle();
        return findById(postsRecord.getId()).orElseThrow();
    }

    @Override
    public Post update(Post post) {
        this.dsl
                .update(POSTS)
                .set(POSTS.TITLE, post.getTitle())
                .set(POSTS.URL, post.getUrl())
                .set(POSTS.CONTENT, post.getContent())
                .set(POSTS.CAT_ID, post.getCategory().getId())
                .set(POSTS.UPDATED_AT, post.getUpdatedAt())
                .where(POSTS.ID.eq(post.getId()))
                .execute();
        return findById(post.getId()).orElseThrow();
    }

    @Override
    public void delete(Long postId) {
        this.dsl.deleteFrom(POSTS).where(POSTS.ID.eq(postId)).execute();
    }

    @Override
    public long count() {
        return this.dsl.fetchCount(POSTS);
    }
}
