package com.sivalabs.techbuzz.posts.domain.repositories;

import com.sivalabs.techbuzz.posts.domain.entities.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            value =
                    "select distinct p.id from Post p join p.category c where c.slug=?1 order by p.id desc",
            countQuery = "select count(p.id) from Post p join p.category c where c.slug=?1")
    Page<Long> findPostIdsByCategorySlug(String categorySlug, Pageable pageable);

    @Query(
            "select distinct p from Post p join p.category c join fetch p.createdBy u left join fetch p.votes where p.id in ?1 order by p.id desc")
    List<Post> findPosts(List<Long> postIds);
}
