package com.sivalabs.techbuzz.posts.domain.repositories;

import com.sivalabs.techbuzz.common.model.PagedResult;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepository {

    PagedResult<Post> findByCategorySlug(String categorySlug, Integer page);

    List<Post> findPosts(List<Long> postIds);

    Optional<Post> findById(Long postId);

    Post save(Post post);

    Post update(Post post);

    void delete(Long postId);

    long count();
}
