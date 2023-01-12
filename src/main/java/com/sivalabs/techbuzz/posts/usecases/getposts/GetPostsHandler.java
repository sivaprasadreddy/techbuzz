package com.sivalabs.techbuzz.posts.usecases.getposts;

import com.sivalabs.techbuzz.common.model.PagedResult;
import com.sivalabs.techbuzz.posts.domain.entities.Category;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GetPostsHandler {
    private static final Integer PAGE_SIZE = 15;

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final PostDtoMapper postDtoMapper;


    @Transactional(readOnly = true)
    public PagedResult<PostDTO> getPosts(Integer page) {
        log.debug("process=get_posts, page={}", page);
        return convert(postRepository.findPosts(getPageable(page)));
    }

    @Transactional(readOnly = true)
    public PagedResult<PostDTO> searchPosts(String query, Integer page) {
        log.debug("process=search_posts, query={}, page={}", query, page);
        return convert(postRepository.searchPosts(query, getPageable(page)));
    }

    @Transactional(readOnly = true)
    public PagedResult<PostDTO> getPostsByCategory(String category, Integer page) {
        log.debug("process=get_posts_by_category, category={}, page={}", category, page);
        return convert(postRepository.findPostsByCategory(category, getPageable(page)));
    }

    private static Pageable getPageable(Integer page) {
        int pageNo = page > 0 ? page - 1 : 0;
        return PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    private PagedResult<PostDTO> convert(Page<Post> postsPage) {
        Page<PostDTO> postDTOPage = postsPage.map(postDtoMapper::toDTO);
        return new PagedResult<>(postDTOPage);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
