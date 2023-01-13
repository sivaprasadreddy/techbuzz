package com.sivalabs.techbuzz.posts.usecases.uploadposts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivalabs.techbuzz.ApplicationProperties;
import com.sivalabs.techbuzz.posts.domain.entities.Category;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.users.domain.User;
import com.sivalabs.techbuzz.users.domain.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UploadPostsHandler {

	private final PostRepository postRepository;

	private final CategoryRepository categoryRepository;

	private final UserRepository userRepository;

	private final ApplicationProperties properties;

	@Async
	public void importPostsAsync(List<String> fileNames) throws IOException {
		postRepository.deleteAll();
		for (String fileName : fileNames) {
			log.info("Importing posts from file: {}", fileName);
			ClassPathResource file = new ClassPathResource(fileName, this.getClass());
			long count = this.importPosts(file.getInputStream());
			log.info("Imported {} posts from file {}", count, fileName);
		}
	}

	public long importPosts(InputStream is) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		PostsData postsData = objectMapper.readValue(is, PostsData.class);
		long count = 0L;
		for (PostEntry postEntry : postsData.posts) {
			Post post = parsePost(postEntry);
			postRepository.save(post);
			count++;
		}
		return count;
	}

	private Post parsePost(PostEntry postEntry) {
		Category category = categoryRepository.findBySlug(postEntry.category).orElseGet(() -> {
			log.info("Category :{} doesn't exist, so saving into 'general' category", postEntry.category);
			return categoryRepository.findBySlug("general").orElseThrow();
		});
		User user = userRepository.findByEmail(properties.adminEmail()).orElseThrow();
		return new Post(null, postEntry.title, postEntry.url, postEntry.content, category, user, LocalDateTime.now(),
				null);
	}

	@Setter
	@Getter
	static class PostsData {

		private List<PostEntry> posts;

	}

	@Setter
	@Getter
	static class PostEntry {

		private String title;

		private String url;

		private String content;

		private String category;

	}

}
