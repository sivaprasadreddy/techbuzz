package com.sivalabs.techbuzz.posts.domain.repositories;

import com.sivalabs.techbuzz.posts.domain.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select p from Post p join p.category c join fetch p.createdBy u where c.slug=?1",
           countQuery = "select count(p) from Post p join p.category c where c.slug=?1")
    Page<Post> findPostsByCategorySlug(String categorySlug, Pageable pageable);
}
