package com.sivalabs.techbuzz.posts.usecases.getposts;

import com.sivalabs.techbuzz.ApplicationProperties;
import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.common.model.PagedResult;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.models.PostDTO;
import com.sivalabs.techbuzz.posts.domain.models.PostUserViewDTO;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.posts.mappers.PostDTOMapper;
import com.sivalabs.techbuzz.security.SecurityService;
import com.sivalabs.techbuzz.users.domain.User;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetPostsHandler {
    private static final Logger log = LoggerFactory.getLogger(GetPostsHandler.class);

    private final PostRepository postRepository;
    private final PostDTOMapper postDtoMapper;
    private final SecurityService securityService;
    private final ApplicationProperties properties;

    public GetPostsHandler(
            PostRepository postRepository,
            PostDTOMapper postDtoMapper,
            SecurityService securityService,
            ApplicationProperties properties) {
        this.postRepository = postRepository;
        this.postDtoMapper = postDtoMapper;
        this.securityService = securityService;
        this.properties = properties;
    }

    public PostDTO getPost(Long postId) {
        log.debug("Fetching post by id: {}", postId);
        return postDtoMapper.toDTO(
                postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found")));
    }

    public PagedResult<PostUserViewDTO> getPostsByCategorySlug(String category, Integer page) {
        log.debug("Fetching posts by category={}, page={}", category, page);
        PagedResult<Post> postPagedResult = postRepository.findByCategorySlug(category, page);
        return convert(postPagedResult);
    }

    private PagedResult<PostUserViewDTO> convert(PagedResult<Post> postsPage) {
        User loginUser = securityService.loginUser();
        List<PostUserViewDTO> postDTOs = postsPage.getData().stream()
                .map(post -> postDtoMapper.toPostUserViewDTO(loginUser, post))
                .toList();
        return new PagedResult<>(
                postDTOs,
                postsPage.getTotalElements(),
                postsPage.getPageNumber(),
                postsPage.getTotalPages(),
                postsPage.isFirst(),
                postsPage.isLast(),
                postsPage.isHasNext(),
                postsPage.isHasPrevious());
    }
}
