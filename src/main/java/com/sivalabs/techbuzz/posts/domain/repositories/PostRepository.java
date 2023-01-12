package com.sivalabs.techbuzz.posts.domain.repositories;

import com.sivalabs.techbuzz.posts.domain.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            value = "select p from Post p JOIN FETCH p.category c join fetch p.createdBy u",
            countQuery = "select count(p) from Post p")
    Page<Post> findPosts(Pageable pageable);

    @Query(
            value =
                    "select p from Post p JOIN FETCH p.category c join fetch p.createdBy u"
                            + " where lower(p.title) like lower(concat('%', :query,'%'))",
            countQuery =
                    "select count(p) from Post p where lower(p.title) like lower(concat('%',"
                            + " :query,'%'))")
    Page<Post> searchPosts(
            @Param("query") String query, Pageable pageable);

    @Query(
            value =
                    "select p from Post p join p.category c join fetch p.createdBy u where"
                            + " c.name=?1",
            countQuery = "select count(p) from Post p join p.category c where c.name=?1")
    Page<Post> findPostsByCategory(String categoryName, Pageable pageable);
}
