package com.sivalabs.techbuzz.posts.usecases.uploadposts;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.sivalabs.techbuzz.posts.domain.entities.Category;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.repositories.CategoryRepository;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.posts.domain.utils.JsoupUtils;
import com.sivalabs.techbuzz.users.domain.User;
import com.sivalabs.techbuzz.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UploadPostsHandler {
    public static final String ADMIN_EMAIL = "sivaprasadreddy.k@gmail.com";

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    @Async
    public void importPostsAsync(List<String> fileNames)
            throws IOException, CsvValidationException {
        postRepository.deleteAll();
        for (String fileName : fileNames) {
            log.info("Importing posts from file: {}", fileName);
            ClassPathResource file = new ClassPathResource(fileName, this.getClass());
            long count = this.importPosts(file.getInputStream());
            log.info("Imported {} posts from file {}", count, fileName);
        }
    }

    public long importPosts(InputStream is) throws IOException, CsvValidationException {
        long count = 0L;

        try (InputStreamReader isr = new InputStreamReader(is, UTF_8);
                CSVReader csvReader = new CSVReader(isr)) {
            csvReader.skip(1);
            CSVIterator iterator = new CSVIterator(csvReader);

            while (iterator.hasNext()) {
                String[] postTokens = iterator.next();
                Post post = parsePost(postTokens);
                postRepository.save(post);
                count++;
            }
        }
        return count;
    }

    private Post parsePost(String[] postTokens) {
        Category category = null;
        if (postTokens.length > 2 && StringUtils.trimToNull(postTokens[2]) != null) {
            String categoryName = StringUtils.trimToEmpty(postTokens[2].split("\\|")[0]);
            categoryRepository.upsert(new Category(null, categoryName));
            category = categoryRepository.findByName(categoryName).orElseThrow();
        }
        String url = postTokens[0];
        String title = postTokens[1];
        if (StringUtils.isEmpty(title)) {
            title = JsoupUtils.getTitle(url);
        }
        User user = userRepository.findByEmail(ADMIN_EMAIL).orElseThrow();
        return new Post(null, title, url, title, category, user, LocalDateTime.now(), null);
    }
}
