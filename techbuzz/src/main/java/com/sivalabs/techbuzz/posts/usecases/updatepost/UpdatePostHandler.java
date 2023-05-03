package com.sivalabs.techbuzz.posts.usecases.updatepost;

import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdatePostHandler {
    private static final Logger log = LoggerFactory.getLogger(UpdatePostHandler.class);

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public UpdatePostHandler(final PostRepository postRepository, final CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    public Post updatePost(UpdatePostRequest request) {
        log.debug("Update post with id={}", request.id());
        Post post = postRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("Post with id: " + request.id() + " not found"));
        Category category = new Category(request.categoryId());
        post.setCategory(category);
        post.setTitle(request.title());
        post.setUrl(request.url());
        post.setContent(request.content());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.update(post);
    }
}
