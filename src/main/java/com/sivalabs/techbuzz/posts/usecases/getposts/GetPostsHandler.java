package com.sivalabs.techbuzz.posts.usecases.getposts;

import com.sivalabs.techbuzz.ApplicationProperties;
import com.sivalabs.techbuzz.common.model.PagedResult;
import com.sivalabs.techbuzz.posts.domain.entities.Category;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.security.SecurityService;
import com.sivalabs.techbuzz.users.domain.User;
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

	private final PostRepository postRepository;

	private final CategoryRepository categoryRepository;

	private final PostDtoMapper postDtoMapper;

	private final SecurityService securityService;

	private final ApplicationProperties properties;

	@Transactional(readOnly = true)
	public PagedResult<PostDTO> getPostsByCategorySlug(String category, Integer page) {
		log.debug("process=get_posts_by_category_slug, category={}, page={}", category, page);
		return convert(postRepository.findPostsByCategorySlug(category, getPageable(page)));
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll(Sort.by("displayOrder"));
	}

	public Category getCategory(String categorySlug) {
		return categoryRepository.findBySlug(categorySlug).orElseThrow();
	}

	private Pageable getPageable(Integer page) {
		int pageNo = page > 0 ? page - 1 : 0;
		return PageRequest.of(pageNo, properties.postsPerPage(), Sort.by(Sort.Direction.DESC, "createdAt"));
	}

	private PagedResult<PostDTO> convert(Page<Post> postsPage) {
		User loginUser = securityService.loginUser();
		Page<PostDTO> postDTOPage = postsPage.map(post -> postDtoMapper.toDTO(loginUser, post));
		return new PagedResult<>(postDTOPage);
	}

}
