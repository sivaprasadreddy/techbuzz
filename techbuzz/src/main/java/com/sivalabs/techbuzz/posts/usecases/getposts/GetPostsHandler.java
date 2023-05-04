package com.sivalabs.techbuzz.posts.usecases.getposts;

import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.common.model.PagedResult;
import com.sivalabs.techbuzz.posts.domain.dtos.PostViewDTO;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.posts.mappers.PostMapper;
import com.sivalabs.techbuzz.security.SecurityService;
import com.sivalabs.techbuzz.users.domain.models.User;
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
    private final PostMapper postMapper;
    private final SecurityService securityService;

    public GetPostsHandler(PostRepository postRepository, PostMapper postMapper, SecurityService securityService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.securityService = securityService;
    }

    public Post getPost(Long postId) {
        log.debug("Fetching post by id: {}", postId);
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    public PagedResult<PostViewDTO> getPostsByCategorySlug(String category, Integer page) {
        log.debug("Fetching posts by category={}, page={}", category, page);
        PagedResult<Post> postPagedResult = postRepository.findByCategorySlug(category, page);
        return convert(postPagedResult);
    }

    private PagedResult<PostViewDTO> convert(PagedResult<Post> postsPage) {
        User loginUser = securityService.loginUser();
        List<PostViewDTO> postDTOs = postsPage.getData().stream()
                .map(post -> postMapper.toPostViewDTO(loginUser, post))
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
