package com.sivalabs.techbuzz.posts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivalabs.techbuzz.ApplicationProperties;
import com.sivalabs.techbuzz.posts.domain.models.Category;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.users.domain.models.User;
import com.sivalabs.techbuzz.users.domain.services.UserService;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PostsDataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(PostsDataInitializer.class);

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final ApplicationProperties properties;

    public PostsDataInitializer(
            PostRepository postRepository,
            CategoryRepository categoryRepository,
            UserService userService,
            ApplicationProperties properties) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.properties = properties;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> filePaths = properties.importFilePaths();
        if (filePaths == null || filePaths.isEmpty()) {
            return;
        }
        this.importPostsAsync(filePaths);
    }

    public void importPostsAsync(List<String> fileNames) throws IOException {
        if (postRepository.count() > 0) {
            log.info("There are existing posts, importing default data is skipped");
            return;
        }
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
        User user = userService.getUserByEmail(properties.adminEmail()).orElseThrow();
        long count = 0L;
        for (PostEntry postEntry : postsData.posts) {
            Post post = convertToPost(postEntry, user);
            postRepository.save(post);
            count++;
        }
        return count;
    }

    private Post convertToPost(PostEntry postEntry, User user) {
        Category category = categoryRepository.findBySlug(postEntry.category).orElseGet(() -> {
            log.info("Category :{} doesn't exist, so saving into 'general' category", postEntry.category);
            return categoryRepository.findBySlug("general").orElseThrow();
        });
        return new Post(
                null,
                postEntry.title,
                postEntry.url,
                postEntry.content,
                category,
                user,
                Set.of(),
                LocalDateTime.now(),
                null);
    }

    record PostsData(List<PostEntry> posts) {}

    record PostEntry(String title, String url, String content, String category) {}
}
