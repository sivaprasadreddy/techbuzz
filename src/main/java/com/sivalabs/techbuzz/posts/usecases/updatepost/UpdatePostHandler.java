package com.sivalabs.techbuzz.posts.usecases.updatepost;

import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.posts.domain.entities.Category;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdatePostHandler {

	private static final Logger logger = LoggerFactory.getLogger(UpdatePostHandler.class);

	private final PostRepository postRepository;

	private final CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Optional<Post> getPostById(Long id) {
		logger.debug("process=get_post_by_id, id={}", id);
		return postRepository.findById(id);
	}

	public Post updatePost(UpdatePostRequest updatePostRequest) {
		logger.debug("process=update_post, id={}", updatePostRequest.id());
		Post post = postRepository.findById(updatePostRequest.id()).orElse(null);
		if (post == null) {
			throw new ResourceNotFoundException("Post with id: " + updatePostRequest.id() + " not found");
		}
		Category category = categoryRepository.getReferenceById(updatePostRequest.categoryId());
		post.setCategory(category);
		post.setTitle(updatePostRequest.title());
		post.setUrl(updatePostRequest.url());
		post.setContent(updatePostRequest.content());
		post.setUpdatedAt(LocalDateTime.now());
		return postRepository.save(post);
	}

}
