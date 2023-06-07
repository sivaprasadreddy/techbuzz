package com.sivalabs.techbuzz.posts.domain.services;

import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.common.model.PagedResult;
import com.sivalabs.techbuzz.posts.domain.dtos.CreatePostRequest;
import com.sivalabs.techbuzz.posts.domain.dtos.CreateVoteRequest;
import com.sivalabs.techbuzz.posts.domain.dtos.PostViewDTO;
import com.sivalabs.techbuzz.posts.domain.dtos.UpdatePostRequest;
import com.sivalabs.techbuzz.posts.domain.mappers.PostMapper;
import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.models.Vote;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.posts.domain.repositories.VoteRepository;
import com.sivalabs.techbuzz.security.SecurityService;
import com.sivalabs.techbuzz.users.domain.models.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {
    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    private final SecurityService securityService;
    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final PostMapper postMapper;

    public PostService(
            SecurityService securityService,
            PostRepository postRepository,
            VoteRepository voteRepository,
            PostMapper postMapper) {
        this.securityService = securityService;
        this.postRepository = postRepository;
        this.voteRepository = voteRepository;
        this.postMapper = postMapper;
    }

    public Post getPost(Long postId) {
        log.debug("Fetching post by id: {}", postId);
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    public PostViewDTO getPostViewDTO(Long postId) {
        Post post = this.getPost(postId);
        return convert(post);
    }

    public PagedResult<PostViewDTO> getPostsByCategorySlug(String categorySlug, Integer page) {
        log.debug("Fetching posts by categorySlug={}, page={}", categorySlug, page);
        PagedResult<Post> postPagedResult = postRepository.findByCategorySlug(categorySlug, page);
        return convert(postPagedResult);
    }

    public PagedResult<PostViewDTO> getCreatedPostsByUser(Long userId, Integer page) {
        log.debug("Fetching post by user id: {}", userId);
        PagedResult<Post> postPagedResult = postRepository.findCreatedPostsByUser(userId, page);
        return convert(postPagedResult);
    }

    public PagedResult<PostViewDTO> getVotedPostsByUser(Long userId, Integer page) {
        log.debug("Fetching post by user id: {}", userId);
        PagedResult<Post> postPagedResult = postRepository.findVotedPostsByUser(userId, page);
        return convert(postPagedResult);
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

    public Post createPost(CreatePostRequest createPostRequest) {
        log.info("Create post with title={}", createPostRequest.title());
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

    public void deletePost(Long postId) {
        log.debug("Deleting post with id: {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        voteRepository.deleteVotesForPost(post.getId());
        postRepository.delete(post.getId());
    }

    public void addVote(CreateVoteRequest request) {
        log.debug("Adding vote :{} for postId: {} by userId:{}", request.value(), request.postId(), request.userId());
        Optional<Vote> voteOptional = voteRepository.findByPostIdAndUserId(request.postId(), request.userId());
        if (voteOptional.isEmpty()) {
            Vote vote = new Vote(null, request.userId(), request.postId(), request.value(), LocalDateTime.now(), null);
            voteRepository.save(vote);
            log.info("Vote saved successfully");
            return;
        }
        Vote existingVote = voteOptional.get();
        existingVote.setValue(request.value());
        voteRepository.update(existingVote);
        log.info("Vote update successfully");
    }

    private PagedResult<PostViewDTO> convert(PagedResult<Post> postsPage) {
        User loginUser = securityService.loginUser();
        List<PostViewDTO> postDTOs = postsPage.data().stream()
                .map(post -> postMapper.toPostViewDTO(loginUser, post))
                .toList();
        return new PagedResult<>(
                postDTOs,
                postsPage.totalElements(),
                postsPage.pageNumber(),
                postsPage.totalPages(),
                postsPage.isFirst(),
                postsPage.isLast(),
                postsPage.hasNext(),
                postsPage.hasPrevious());
    }

    private PostViewDTO convert(Post post) {
        User loginUser = securityService.loginUser();
        return postMapper.toPostViewDTO(loginUser, post);
    }
}
