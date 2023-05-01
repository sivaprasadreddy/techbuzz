package com.sivalabs.techbuzz.posts.usecases.createpost;

import com.sivalabs.techbuzz.posts.domain.entities.Category;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.models.PostDTO;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.posts.mappers.PostDTOMapper;
import com.sivalabs.techbuzz.users.domain.User;
import com.sivalabs.techbuzz.users.domain.UserRepository;
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
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PostDTOMapper postDTOMapper;

    public CreatePostHandler(
            PostRepository postRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository,
            PostDTOMapper postDTOMapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.postDTOMapper = postDTOMapper;
    }

    public PostDTO createPost(CreatePostRequest createPostRequest) {
        String title = createPostRequest.title();
        log.info("process=create_post, title={}", title);
        Category category = categoryRepository.getReferenceById(createPostRequest.categoryId());
        User user = new User();
        user.setId(createPostRequest.createdUserId());
        Post post = new Post(
                null,
                title,
                createPostRequest.url(),
                createPostRequest.content(),
                category,
                user,
                Set.of(),
                LocalDateTime.now(),
                null);
        return postDTOMapper.toDTO(postRepository.save(post));
    }
}
