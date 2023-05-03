package com.sivalabs.techbuzz.posts.usecases.createpost;

import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.users.domain.models.User;
import java.time.LocalDateTime;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreatePostHandler {
    private static final Logger log = LoggerFactory.getLogger(CreatePostHandler.class);

    private final PostRepository postRepository;

    public CreatePostHandler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(CreatePostRequest createPostRequest) {
        log.info("process=create_post, title={}", createPostRequest.title());
        Category category = new Category(createPostRequest.categoryId());
        User user = new User(createPostRequest.createdUserId());
        Post post = new Post(
                null,
                createPostRequest.title(),
                createPostRequest.url(),
                createPostRequest.content(),
                category,
                user,
                Set.of(),
                LocalDateTime.now(),
                null);
        return postRepository.save(post);
    }
}
