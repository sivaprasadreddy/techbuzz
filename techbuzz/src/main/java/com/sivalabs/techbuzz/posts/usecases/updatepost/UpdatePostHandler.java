package com.sivalabs.techbuzz.posts.usecases.updatepost;

import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.posts.domain.entities.Category;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.models.PostDTO;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.posts.mappers.PostDTOMapper;
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
    private final PostDTOMapper postDTOMapper;

    public UpdatePostHandler(
            final PostRepository postRepository,
            final CategoryRepository categoryRepository,
            final PostDTOMapper postDTOMapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.postDTOMapper = postDTOMapper;
    }

    public PostDTO updatePost(UpdatePostRequest request) {
        log.debug("Update post with id={}", request.id());
        Post post = postRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("Post with id: " + request.id() + " not found"));
        Category category = categoryRepository.getReferenceById(request.categoryId());
        post.setCategory(category);
        post.setTitle(request.title());
        post.setUrl(request.url());
        post.setContent(request.content());
        post.setUpdatedAt(LocalDateTime.now());
        return postDTOMapper.toDTO(postRepository.save(post));
    }
}
